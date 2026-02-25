import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Servidor (ObjectStreams) - "Oráculo" de Números
 *
 * Qué hace:
 * - Acepta clientes por el puerto 6000.
 * - Atiende 1 cliente por turno (si llega otro, se queda en cola).
 * - El cliente envía un objeto Numeros con un entero.
 * - El servidor calcula cuadrado y cubo y devuelve el mismo objeto rellenado.
 *
 * Cosas que controla (para que no se cuelgue):
 * - Timeout AFK: si el cliente no envía nada en X segundos cuando es su turno, se expulsa.
 * - Timeout de cola: si un cliente espera demasiado para ser atendido, se le informa y se cierra.
 */
public class SocketServer {
    // Tiempo máximo para que el cliente envíe algo cuando ya le toca
    private static final int TIMEOUT_LECTURA_CLIENTE_MS = 15_000;

    // Tiempo máximo esperando turno
    private static final int TIMEOUT_COLA_MS = 30_000;

    // "Turno" para atender 1 cliente a la vez
    private static final Semaphore SEMAFORO_TURNO = new Semaphore(1, true);

    public static void main(String[] args) throws IOException {
        final int puerto = 6000;

        // Pool de hilos para no bloquear el accept() con un cliente
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en puerto " + puerto);

            while (true) {
                System.out.println("---Esperando clientes---");
                Socket cliente = servidor.accept();
                pool.submit(() -> atenderCliente(cliente));
            }
        } finally {
            pool.shutdownNow();
            System.out.println("Servidor cerrado.");
        }
    }

    /**
     * Atiende a un cliente:
     * - Le manda mensajes de estado (cola/turno)
     * - Recibe un Numeros
     * - Calcula y devuelve
     */
    private static void atenderCliente(Socket cliente) {
        String remoto = String.valueOf(cliente.getRemoteSocketAddress());

        // Configuración del socket para evitar bloqueos infinitos
        try {
            cliente.setSoTimeout(TIMEOUT_LECTURA_CLIENTE_MS);
        } catch (SocketException e) {
            cerrarSilencioso(cliente);
            return;
        }

        // Importante: creamos primero el ObjectOutputStream para poder mandar mensajes
        try (Socket s = cliente;
             ObjectOutputStream salida = new ObjectOutputStream(s.getOutputStream())) {

            System.out.println("Cliente conectado: " + remoto);

            // Mensaje inicial: si hay alguien, vas a cola
            if (SEMAFORO_TURNO.availablePermits() == 0) {
                escribirObjetoSeguro(salida, "Servidor ocupado: estás en cola...");
            } else {
                escribirObjetoSeguro(salida, "Servidor listo: esperando tu numero...");
            }

            // Espera de turno con timeout
            boolean tieneTurno;
            try {
                tieneTurno = SEMAFORO_TURNO.tryAcquire(TIMEOUT_COLA_MS, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                escribirObjetoSeguro(salida, "Cancelado: se interrumpió la espera.");
                return;
            }

            if (!tieneTurno) {
                escribirObjetoSeguro(salida, "Timeout: demasiada cola. Intenta más tarde.");
                return;
            }

            try {
                // Ya le toca
                escribirObjetoSeguro(salida, "Tu turno: envía el objeto Numeros.");

                // Ahora sí leemos el objeto
                try (ObjectInputStream entrada = new ObjectInputStream(s.getInputStream())) {
                    final Object recibido;
                    try {
                        recibido = entrada.readObject();
                    } catch (SocketTimeoutException e) {
                        System.err.println("Cliente AFK, expulsando: " + remoto);
                        escribirObjetoSeguro(salida, "Expulsado por inactividad (timeout).");
                        return;
                    }

                    if (recibido instanceof Numeros numeros) {
                        int n = numeros.getNumero();

                        // Cálculos (usamos long para no desbordar tan fácil)
                        numeros.setCuadrado((long) n * n);
                        numeros.setCubo((long) n * n * n);

                        escribirObjetoSeguro(salida, numeros);
                        System.out.println("Procesado y devuelto a " + remoto + ": " + n);
                    } else {
                        escribirObjetoSeguro(salida, "Petición inválida: se esperaba un objeto Numeros.");
                    }
                }

            } finally {
                SEMAFORO_TURNO.release();
                System.out.println("Fin de atención para: " + remoto);
            }

        } catch (EOFException | SocketException e) {
            System.err.println("Cliente desconectado: " + remoto);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error con " + remoto + ": " + e.getMessage());
        }
    }

    // Enviar sin romper el hilo si el cliente se desconecta
    private static void escribirObjetoSeguro(ObjectOutputStream salida, Object obj) {
        try {
            salida.writeObject(obj);
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
}