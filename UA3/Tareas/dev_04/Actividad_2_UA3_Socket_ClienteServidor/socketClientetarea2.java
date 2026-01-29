import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Terminal de usuario para conectar con el servidor central
 * y analizar los datos del socket establecido.
 */
public class socketClientetarea2 {
    private static final int SERVER_PORT = 6000;

    public static void main(String[] args) {
        Socket canalComunicacion = null;
        Scanner lectorTeclado = new Scanner(System.in);
        String ipServidor;

        try {
            System.out.println("--- INICIO DE TERMINAL TCP ---");
            System.out.print("Escriba la dirección del servidor (Vacío = local): ");
            ipServidor = lectorTeclado.nextLine().trim();

            // Lógica de dirección por defecto usando ternario o if simple
            if (ipServidor.length() == 0) {
                ipServidor = "127.0.0.1"; // Uso de IP en lugar de localhost
            }

            System.out.println("Conectando a " + ipServidor + "...");

            // Apertura del socket hacia el servidor
            canalComunicacion = new Socket(ipServidor, SERVER_PORT);
            System.out.println("¡Enlace establecido exitosamente!");

            // Ejecución de métodos de análisis y recepción
            reportarEstadoSocket(canalComunicacion);
            escucharServidor(canalComunicacion);

            System.out.println("\nPulsa [ENTER] para terminar la sesión.");
            lectorTeclado.nextLine();

        } catch (UnknownHostException e) {
            System.err.println("Servidor no encontrado.");
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } finally {
            // Limpieza de objetos de entrada y red
            if (lectorTeclado != null) lectorTeclado.close();

            if (canalComunicacion != null && !canalComunicacion.isClosed()) {
                try {
                    canalComunicacion.close();
                    System.out.println("Conexión finalizada por el usuario.");
                } catch (IOException e) {
                    System.err.println("Error al cerrar canal.");
                }
            }
        }
    }

    private static void reportarEstadoSocket(Socket s) {
        System.out.println("\n--- Análisis del Enlace ---");
        System.out.println(" > Mi Puerto Local: " + s.getLocalPort());
        System.out.println(" > Puerto del Servidor: " + s.getPort());

        // Resolución de IP remota
        InetAddress remoto = s.getInetAddress();
        System.out.println(" > IP del Nodo Central: " + remoto.getHostAddress());
        System.out.println(" > Nombre del Nodo: " + remoto.getHostName());

        // Estado del socket
        System.out.println(" > ¿Enlace activo?: " + s.isConnected());
    }

    private static void escucharServidor(Socket s) {
        try {
            // Obtención del flujo de entrada
            DataInputStream streamEntrada = new DataInputStream(s.getInputStream());
            String respuesta = streamEntrada.readUTF();
            System.out.println("\n[MENSAJE RECIBIDO]: " + respuesta);
        } catch (IOException error) {
            System.err.println("Fallo al leer datos del servidor.");
        }
    }
}