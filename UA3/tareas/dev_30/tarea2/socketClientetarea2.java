import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Cliente TCP que se conecta al servidor y muestra información de puertos
 * local, remoto y dirección IP del cliente.
 */
public class socketClientetarea2 {
    private static final int PUERTO = 6000;

    public static void main(String[] args) {
        Socket socket = null;
        Scanner sc = new Scanner(System.in);
        String host = "";

        try {
            System.out.println("=== CLIENTE TCP ===");
            System.out.print("Introduce la dirección IP o Host del servidor (ej. localhost): ");
            host = sc.nextLine();

            // Si el usuario deja vacío, usar localhost por defecto
            if (host.trim().isEmpty()) {
                host = "localhost";
            }

            System.out.println("Intentando conectar al servidor " + host + ":" + PUERTO + "...\n");

            // Crear socket y conectar al servidor
            socket = new Socket(host, PUERTO);

            System.out.println("¡Conexión establecida con éxito!\n");

            // Mostrar información de la conexión
            mostrarInfoConexion(socket);

            // Recibir mensaje del servidor
            recibirMensaje(socket);

            System.out.println("\nPresiona Enter para desconectar...");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        } catch (UnknownHostException e) {
            System.err.println("Error: No se pudo encontrar el host " + host);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar el scanner
            if (sc != null) {
                sc.close();
            }
            // Cerrar el socket
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                    System.out.println("Desconectado del servidor.");
                } catch (IOException e) {
                    System.err.println("Error I/O al cerrar el socket: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Muestra información detallada sobre la conexión establecida.
     * Utiliza getInetAddress() para obtener la dirección IP del servidor.
     */
    private static void mostrarInfoConexion(Socket socket) {
        System.out.println("[INFORMACIÓN DE LA CONEXION]");

        // Puerto local del cliente
        int puertoLocal = socket.getLocalPort();
        System.out.println("  Puerto Local (Cliente):   " + puertoLocal);

        // Puerto remoto del servidor
        int puertoRemoto = socket.getPort();
        System.out.println("  Puerto Remoto (Servidor): " + puertoRemoto);

        // Dirección IP del servidor usando getInetAddress()
        InetAddress direccionServidor = socket.getInetAddress();
        System.out.println("  Dirección IP Servidor:    " + direccionServidor.getHostAddress());
        System.out.println("  Nombre del Host:          " + direccionServidor.getHostName());

        // Información adicional
        System.out.println("\n  Dirección Local:          " + socket.getLocalAddress().getHostAddress());
        System.out.println("  Socket Conectado:         " + socket.isConnected());
        System.out.println("  Socket Cerrado:           " + socket.isClosed());
    }

    /**
     * Recibe y muestra un mensaje del servidor.
     */
    private static void recibirMensaje(Socket socket) {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            String mensaje = entrada.readUTF();
            System.out.println("[Servidor] " + mensaje);
        } catch (IOException e) {
            System.err.println("Error al recibir mensaje del servidor: " + e.getMessage());
        }
    }
}
