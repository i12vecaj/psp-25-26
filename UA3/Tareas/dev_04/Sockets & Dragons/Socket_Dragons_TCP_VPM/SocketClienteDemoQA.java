import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SocketClienteDemoQA
 *
 * Objetivo:
 * - Demostrar en clase que el servidor maneja:
 *   1) Cola/turnos (solo 1 cliente atendido a la vez)
 *   2) Timeouts/AFK (expulsa si no envías cuando es tu turno)
 *   3) Responde el mensaje en mayúsculas
 *
 * Cómo usar:
 * - Ejecuta el servidor.
 * - Ejecuta este cliente.
 * - Verás que algunos entran en cola y luego reciben "Tu turno".
 */
public class SocketClienteDemoQA {
    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int puerto = 6000;

        final int clientes = 20;
        final int hilos = 20;

        // Si pones esto en true, el último cliente simula AFK cuando recibe "Tu turno"
        final boolean Afk = true;

        ExecutorService pool = Executors.newFixedThreadPool(hilos);
        List<Future<?>> tasks = new ArrayList<>();

        try {
            for (int i = 1; i <= clientes; i++) {
                final int id = i;
                final boolean afkEste = Afk && (i == clientes);
                tasks.add(pool.submit(() -> ejecutarCliente(host, puerto, id, afkEste)));

                // Pequeña pausa para que se vea el orden de cola en consola
                Thread.sleep(150);
            }

            // Esperar fin
            for (Future<?> f : tasks) {
                f.get();
            }

        } finally {
            pool.shutdownNow();
        }
    }

    private static void ejecutarCliente(String host, int puerto, int id, boolean modoAfk) {
        // Timeout de lectura del cliente: debe ser suficiente para leer mensajes de estado
        final int timeoutLecturaMs = 20_000;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, puerto), 5_000);
            socket.setSoTimeout(timeoutLecturaMs);

            try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                log(id, "Conectado");

                // 1) Leer mensajes de estado hasta "Tu turno" o hasta que el server termine la sesión
                while (true) {
                    String msg = in.readUTF();
                    log(id, "[Servidor] " + msg);

                    if (msg.startsWith("Timeout") || msg.startsWith("Cancelado") || msg.startsWith("Expulsado")) {
                        log(id, "Sesión terminada por server: " + msg);
                        return;
                    }

                    if (msg.startsWith("Tu turno")) {
                        break;
                    }
                }

                // 2) Si este cliente es el AFK, no envía nada.
                if (modoAfk) {
                    log(id, "Modo AFK: no enviaré mensaje. Espero a que el server me expulse...");

                    // Leemos hasta expulsión o hasta que cierre
                    while (true) {
                        String msg = in.readUTF();
                        log(id, "[Servidor] " + msg);
                        if (msg.startsWith("Expulsado") || msg.startsWith("Timeout") || msg.startsWith("Cancelado")) {
                            log(id, "AFK finalizado: " + msg);
                            return;
                        }
                    }
                }

                // 3) Enviar un mensaje corto (válido) y recibir respuesta
                String payload = "hola cliente " + id;
                out.writeUTF(payload);
                out.flush();

                String respuesta = in.readUTF();
                log(id, "Respuesta: " + respuesta);

                // Validación simple
                if (!respuesta.equals(payload.toUpperCase())) {
                    log(id, "(Aviso) Respuesta inesperada");
                }
            }

        } catch (SocketTimeoutException e) {
            log(id, "Timeout en cliente esperando datos del servidor");
        } catch (Exception e) {
            log(id, "Error: " + e.getMessage());
        }
    }

    private static void log(int id, String msg) {
        System.out.println("[QA #" + id + "] " + msg);
    }
}
