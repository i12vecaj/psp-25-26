import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Cliente TCP (UTF) - Cliente 1
 *
 * Qué hace:
 * - Se conecta al servidor.
 * - Lee mensajes de estado (cola/turno).
 * - Cuando es su turno, pide un mensaje al usuario, lo envía y muestra la respuesta.
 *
 * Cosas que controla:
 * - Timeout por si el servidor no responde.
 * - Mensajes del servidor tipo "Timeout" / "Expulsado".
 */
public class SocketClienteEco {
    private static final int TIMEOUT_LECTURA_SERVIDOR_MS = 10_000;

    public static void main(String[] args) {
        final String host = "localhost";
        final int puerto = 6000;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, puerto), 5_000);
            socket.setSoTimeout(TIMEOUT_LECTURA_SERVIDOR_MS);

            try (DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
                 DataInputStream entrada = new DataInputStream(socket.getInputStream());
                 Scanner sc = new Scanner(System.in)) {

                System.out.println("Conectado al servidor en " + host + ":" + puerto);

                esperarTurno(entrada);

                String mensaje = leerLineaNoVacia(sc, "Escribe el mensaje a enviar: ");
                salida.writeUTF(mensaje);
                salida.flush();

                String respuesta = entrada.readUTF();
                System.out.println("Respuesta del servidor:\n\t" + respuesta);
            }

        } catch (SocketTimeoutException e) {
            System.err.println("Timeout: el servidor no respondió a tiempo.");
        } catch (ConnectException e) {
            System.err.println("No se pudo conectar al servidor: " + e.getMessage());
        } catch (EOFException e) {
            System.err.println("Conexión cerrada por el servidor.");
        } catch (IOException e) {
            System.err.println("Error de red: " + e.getMessage());
        }
    }

    private static void esperarTurno(DataInputStream entrada) throws IOException {
        while (true) {
            String msg = entrada.readUTF();
            System.out.println("[Servidor] " + msg);

            if (msg.startsWith("Timeout") || msg.startsWith("Cancelado") || msg.startsWith("Expulsado") || msg.startsWith("IP bloqueada")) {
                throw new EOFException(msg);
            }

            if (msg.startsWith("Tu turno")) {
                return;
            }
        }
    }

    private static String leerLineaNoVacia(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            if (line != null && !line.isBlank()) return line;
            System.out.println("El mensaje no puede estar vacío.");
        }
    }
}