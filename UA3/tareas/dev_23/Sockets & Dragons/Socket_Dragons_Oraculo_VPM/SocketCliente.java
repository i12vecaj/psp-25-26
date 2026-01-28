import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Cliente (ObjectStreams) - Cliente 1
 *
 * Qué hace:
 * - Se conecta al servidor.
 * - Espera su turno (el servidor manda mensajes de estado).
 * - Pide un entero por consola.
 * - Envía un objeto Numeros con ese entero.
 * - Recibe el objeto con cuadrado y cubo y lo muestra.
 *
 * Cosas que controla:
 * - Timeout esperando respuesta del servidor.
 */
public class SocketCliente {
    // Tiempo máximo esperando respuesta del servidor
    private static final int TIMEOUT_RESPUESTA_SERVIDOR_MS = 10_000;

    public static void main(String[] args) {
        final String host = "localhost";
        final int puerto = 6000;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, puerto), 5_000);
            socket.setSoTimeout(TIMEOUT_RESPUESTA_SERVIDOR_MS);

            // Importante: en ObjectStreams, se crea primero salida para evitar bloqueos
            try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                 Scanner sc = new Scanner(System.in)) {

                System.out.println("Conectado al servidor en " + host + ":" + puerto);

                // Esperamos mensajes del servidor hasta que nos toque
                esperarTurno(entrada);

                int n = leerEntero(sc, "Introduce un numero entero: ");
                salida.writeObject(new Numeros(n));
                salida.flush();

                Object respuesta;
                try {
                    respuesta = entrada.readObject();
                } catch (SocketTimeoutException e) {
                    System.err.println("Timeout: el servidor no respondió a tiempo.");
                    return;
                }

                if (respuesta instanceof Numeros num) {
                    System.out.println("Respuesta del servidor:");
                    System.out.println("\tNumero: " + num.getNumero());
                    System.out.println("\tCuadrado: " + num.getCuadrado());
                    System.out.println("\tCubo: " + num.getCubo());
                } else {
                    // A veces el servidor responde con String (cola/timeout/invalid)
                    System.out.println("Respuesta del servidor:\n\t" + respuesta);
                }
            }

        } catch (EOFException e) {
            System.err.println("Conexión cerrada por el servidor.");
        } catch (ConnectException e) {
            System.err.println("No se pudo conectar al servidor: " + e.getMessage());
        } catch (SocketTimeoutException e) {
            System.err.println("Timeout de conexión/lectura: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de red: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Respuesta inválida del servidor: " + e.getMessage());
        }
    }

    // Lee mensajes del servidor (String) hasta que diga "Tu turno"
    private static void esperarTurno(ObjectInputStream entrada) throws IOException, ClassNotFoundException {
        while (true) {
            Object msg = entrada.readObject();

            if (msg instanceof String s) {
                System.out.println("[Servidor] " + s);

                // Si el servidor termina la sesión, dejamos de esperar
                if (s.startsWith("Timeout") || s.startsWith("Cancelado") || s.startsWith("Expulsado")) {
                    throw new EOFException(s);
                }

                if (s.startsWith("Tu turno")) {
                    return;
                }

                continue;
            }

            // Si llega algo raro, lo mostramos y seguimos
            System.out.println("[Servidor] " + msg);
            return;
        }
    }

    private static int leerEntero(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debe ser un entero.");
            }
        }
    }
}