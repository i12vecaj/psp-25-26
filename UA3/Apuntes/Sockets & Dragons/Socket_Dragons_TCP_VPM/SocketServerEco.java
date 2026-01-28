import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servidor TCP (UTF) - Versión "robusta"
 *
 * Qué hace:
 * - Acepta conexiones en el puerto 6000.
 * - Manda mensajes de estado (listo/cola/turno).
 * - Recibe un texto (UTF) y lo devuelve en MAYÚSCULAS.
 *
 * Qué intenta evitar:
 * - Que se quede colgado si un cliente no envía nada (timeout).
 * - Que entren demasiados clientes a la vez (límites + cola).
 * - Que entren mensajes vacíos o demasiado largos.
 * - Que una IP que falla muchas veces siga molestando (bloqueo temporal sencillo).
 */
public class SocketServerEco {
    // -------------------------
    // Configuración "simple"
    // -------------------------

    private static final int PUERTO = 6000;

    // Tiempo máximo para que el cliente envíe el mensaje cuando ya es su turno
    private static final int TIMEOUT_LECTURA_CLIENTE_MS = 15_000;

    // Tiempo máximo esperando en cola para conseguir turno
    private static final int TIMEOUT_COLA_MS = 30_000;

    // Límite de caracteres del mensaje (para no procesar cosas enormes)
    private static final int MAX_CARACTERES_MENSAJE = 512;

    // Máximo de conexiones abiertas simultáneamente
    private static final int MAX_CONEXIONES_ACTIVAS = 200;

    // Pool de hilos acotado (para no crear hilos infinitos)
    private static final int MAX_HILOS = 32;
    private static final int TAM_COLA_POOL = 200;

    // Bloqueo temporal por IP si falla varias veces
    private static final int FALLOS_PARA_BLOQUEO = 3;
    private static final long BLOQUEO_MS = 2 * 60_000; // 2 minutos

    // Turno: solo 1 cliente atendido a la vez (como pedías)
    private static final Semaphore SEMAFORO_TURNO = new Semaphore(1, true);

    // Límite de sockets abiertos
    private static final Semaphore SEMAFORO_CONEXIONES = new Semaphore(MAX_CONEXIONES_ACTIVAS, true);

    // "Memoria" de IPs que fallan
    private static final ConcurrentHashMap<String, EstadoIp> ESTADO_IP = new ConcurrentHashMap<>();

    private static final class EstadoIp {
        final AtomicInteger fallos = new AtomicInteger(0);
        volatile long bloqueadoHastaMs = 0L;

        boolean estaBloqueadaAhora() {
            return System.currentTimeMillis() < bloqueadoHastaMs;
        }

        void sumarFallo() {
            int v = fallos.incrementAndGet();
            if (v >= FALLOS_PARA_BLOQUEO) {
                bloqueadoHastaMs = System.currentTimeMillis() + BLOQUEO_MS;
                fallos.set(0);
            }
        }

        void marcarOk() {
            fallos.set(0);
        }
    }

    public static void main(String[] args) throws IOException {
        // Pool acotado: máximo X hilos y cola limitada.
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                Math.min(4, MAX_HILOS),
                MAX_HILOS,
                30L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(TAM_COLA_POOL),
                r -> {
                    Thread t = new Thread(r);
                    t.setName("Servidor-worker");
                    t.setDaemon(true);
                    return t;
                },
                new ThreadPoolExecutor.AbortPolicy()
        );

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en puerto " + PUERTO);

            while (true) {
                System.out.println("---Esperando clientes---");

                Socket socket = servidor.accept();

                // 1) Control simple: no dejar infinitas conexiones abiertas
                if (!SEMAFORO_CONEXIONES.tryAcquire()) {
                    try (Socket s = socket;
                         DataOutputStream salida = new DataOutputStream(s.getOutputStream())) {
                        escribirUTFSeguro(salida, "Servidor saturado: demasiadas conexiones. Intenta más tarde.");
                    } catch (IOException ignored) {
                    }
                    continue;
                }

                // 2) Metemos la conexión en el pool (si el pool está lleno, se rechaza)
                try {
                    pool.submit(() -> atenderCliente(socket));
                } catch (RejectedExecutionException e) {
                    try (Socket s = socket;
                         DataOutputStream salida = new DataOutputStream(s.getOutputStream())) {
                        escribirUTFSeguro(salida, "Servidor ocupado: cola interna llena. Intenta más tarde.");
                    } catch (IOException ignored) {
                    } finally {
                        SEMAFORO_CONEXIONES.release();
                    }
                }
            }
        } finally {
            pool.shutdownNow();
            System.out.println("Servidor cerrado.");
        }
    }

    private static void atenderCliente(Socket cliente) {
        try {
            // Sacamos la IP para el bloqueo sencillo
            String ip = "unknown";
            InetAddress addr = null;
            try { addr = cliente.getInetAddress(); } catch (Exception ignored) {}
            if (addr != null) ip = addr.getHostAddress();

            EstadoIp estadoIp = ESTADO_IP.computeIfAbsent(ip, k -> new EstadoIp());

            // Si está bloqueada, informamos y cerramos
            if (estadoIp.estaBloqueadaAhora()) {
                try (Socket s = cliente;
                     DataOutputStream salida = new DataOutputStream(s.getOutputStream())) {
                    escribirUTFSeguro(salida, "IP bloqueada temporalmente por fallos repetidos. Intenta más tarde.");
                } catch (IOException ignored) {
                }
                return;
            }

            // Timeout de lectura: evita que se quede colgado leyendo
            try {
                cliente.setSoTimeout(TIMEOUT_LECTURA_CLIENTE_MS);
            } catch (SocketException e) {
                cerrarSilencioso(cliente);
                return;
            }

            String remoto = String.valueOf(cliente.getRemoteSocketAddress());

            // Creamos salida pronto para mandar estados
            try (Socket s = cliente;
                 DataOutputStream salida = new DataOutputStream(s.getOutputStream())) {

                System.out.println("Cliente conectado: " + remoto);

                // Mensaje informativo: si hay alguien dentro, vas a cola
                if (SEMAFORO_TURNO.availablePermits() == 0) {
                    escribirUTFSeguro(salida, "Servidor ocupado: estás en cola...");
                } else {
                    escribirUTFSeguro(salida, "Servidor listo: esperando tu mensaje...");
                }

                // Intentamos conseguir el turno con timeout
                boolean tieneTurno;
                try {
                    tieneTurno = SEMAFORO_TURNO.tryAcquire(TIMEOUT_COLA_MS, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    escribirUTFSeguro(salida, "Cancelado: se interrumpió la espera.");
                    estadoIp.sumarFallo();
                    return;
                }

                if (!tieneTurno) {
                    escribirUTFSeguro(salida, "Timeout: demasiada cola. Intenta más tarde.");
                    estadoIp.sumarFallo();
                    return;
                }

                try {
                    escribirUTFSeguro(salida, "Tu turno: envía tu mensaje.");

                    try (DataInputStream entrada = new DataInputStream(s.getInputStream())) {
                        final String mensaje;
                        try {
                            mensaje = entrada.readUTF();
                        } catch (SocketTimeoutException e) {
                            escribirUTFSeguro(salida, "Expulsado por inactividad (timeout).");
                            estadoIp.sumarFallo();
                            return;
                        }

                        // Validaciones básicas
                        if (mensaje == null || mensaje.isBlank()) {
                            escribirUTFSeguro(salida, "Mensaje inválido: está vacío.");
                            estadoIp.sumarFallo();
                            return;
                        }

                        if (mensaje.length() > MAX_CARACTERES_MENSAJE) {
                            escribirUTFSeguro(salida, "Mensaje inválido: demasiado largo (max " + MAX_CARACTERES_MENSAJE + ").");
                            estadoIp.sumarFallo();
                            return;
                        }

                        // Lógica principal
                        escribirUTFSeguro(salida, mensaje.toUpperCase());
                        estadoIp.marcarOk();

                        System.out.println("Recibido de " + remoto + ": " + mensaje);
                    }

                } finally {
                    SEMAFORO_TURNO.release();
                    System.out.println("Fin de atención para: " + remoto);
                }
            }

        } catch (EOFException | SocketException e) {
            // Desconexión del cliente
            System.err.println("Cliente desconectado: " + remotoSeguro(cliente));
            registrarFalloIp(cliente);
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
            registrarFalloIp(cliente);
        } finally {
            // Importante: liberamos el contador de conexiones activas SIEMPRE
            SEMAFORO_CONEXIONES.release();
        }
    }

    // Escribe sin romper el hilo si el cliente se desconecta justo al responder
    private static void escribirUTFSeguro(DataOutputStream salida, String msg) {
        try {
            salida.writeUTF(msg);
            salida.flush();
        } catch (IOException ignored) {
        }
    }

    private static void cerrarSilencioso(Socket s) {
        try {
            s.close();
        } catch (IOException ignored) {
        }
    }

    private static String remotoSeguro(Socket s) {
        try {
            return String.valueOf(s.getRemoteSocketAddress());
        } catch (Exception e) {
            return "unknown";
        }
    }

    private static void registrarFalloIp(Socket s) {
        try {
            InetAddress addr = s.getInetAddress();
            if (addr == null) return;
            String ip = addr.getHostAddress();
            ESTADO_IP.computeIfAbsent(ip, k -> new EstadoIp()).sumarFallo();
        } catch (Exception ignored) {
        }
    }
}
