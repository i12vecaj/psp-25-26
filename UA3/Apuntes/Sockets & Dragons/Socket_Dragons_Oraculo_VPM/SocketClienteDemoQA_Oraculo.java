import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SocketClienteDemoQA_Oraculo
 *
 * Objetivo:
 *   1) Cola/turnos (solo 1 cliente atendido a la vez)
 *   2) Timeouts/AFK (si no envías cuando es tu turno, te expulsa)
 *   3) Respuesta correcta (devuelve Numeros con cuadrado y cubo)
 */
public class SocketClienteDemoQA_Oraculo {

    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int puerto = 6000;

        final int clientes = 10;
        final int hilos = 10;

        // Si es true, el último cliente simula AFK (no envía el objeto tras "Tu turno")
        final boolean demoAfk = true;

        ExecutorService pool = Executors.newFixedThreadPool(hilos);
        List<Future<?>> tasks = new ArrayList<>();

        try {
            for (int i = 1; i <= clientes; i++) {
                final int id = i;
                final boolean afkEste = demoAfk && (i == clientes);
                tasks.add(pool.submit(() -> ejecutarCliente(host, puerto, id, afkEste)));

                // Pausa para visualizar la cola/turno
                Thread.sleep(150);
            }

            for (Future<?> f : tasks) {
                f.get();
            }

        } finally {
            pool.shutdownNow();
        }
    }

    private static void ejecutarCliente(String host, int puerto, int id, boolean modoAfk) {
        final int timeoutLecturaMs = 25_000;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, puerto), 5_000);
            socket.setSoTimeout(timeoutLecturaMs);

            // Importante: primero salida, luego entrada
            try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

                log(id, "Conectado");

                // Esperar mensajes hasta turno
                while (true) {
                    Object msg = entrada.readObject();

                    if (msg instanceof String s) {
                        log(id, "[Servidor] " + s);

                        if (s.startsWith("Timeout") || s.startsWith("Cancelado") || s.startsWith("Expulsado")) {
                            log(id, "Sesión terminada por server: " + s);
                            return;
                        }

                        if (s.startsWith("Tu turno")) {
                            break;
                        }
                        continue;
                    }

                    log(id, "[Servidor] (inesperado) " + msg);
                    break;
                }

                if (modoAfk) {
                    log(id, "Modo AFK DEMO: no enviaré el objeto. Espero expulsión...");

                    while (true) {
                        Object msg = entrada.readObject();
                        if (msg instanceof String s) {
                            log(id, "[Servidor] " + s);
                            if (s.startsWith("Expulsado") || s.startsWith("Timeout") || s.startsWith("Cancelado")) {
                                log(id, "AFK demo finalizado: " + s);
                                return;
                            }
                        } else {
                            log(id, "[Servidor] (inesperado) " + msg);
                        }
                    }
                }

                int n = id + 2;
                salida.writeObject(new Numeros(n));
                salida.flush();

                Object respuesta = entrada.readObject();

                if (respuesta instanceof Numeros num) {
                    log(id, "Respuesta: n=" + num.getNumero() + " cuadrado=" + num.getCuadrado() + " cubo=" + num.getCubo());
                } else {
                    log(id, "Respuesta: " + respuesta);
                }
            }

        } catch (SocketTimeoutException e) {
            log(id, "Timeout en cliente esperando datos del servidor");
        } catch (EOFException e) {
            log(id, "Servidor cerró la conexión");
        } catch (Exception e) {
            log(id, "Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void log(int id, String msg) {
        System.out.println("[DEMO_ORACULO #" + id + "] " + msg);
    }
}
