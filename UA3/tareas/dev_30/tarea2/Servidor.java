import java.io.*;
import java.net.*;

/**
 * Servidor TCP que acepta múltiples clientes y muestra información de puertos
 * local y remoto para cada conexión.
 */
public class Servidor {
    private static final int PUERTO = 6000;

    public static void main(String[] args) {
        ServerSocket servidor = null;
        int contadorClientes = 1;

        try {
            // Crear el servidor en el puerto especificado
            servidor = new ServerSocket(PUERTO);
            System.out.println("=== SERVIDOR TCP INICIADO ===");
            System.out.println("Esperando conexiones en el puerto " + PUERTO + "...");
            System.out.println("Presione Ctrl+C para detener el servidor.\n");

            // Aceptar clientes indefinidamente
            while (true) {
                System.out.println("Esperando cliente " + contadorClientes + "...");
                Socket clienteSocket = servidor.accept();

                // Mostrar información del cliente conectado
                mostrarInfoCliente(clienteSocket, contadorClientes);

                // Enviar mensaje de bienvenida al cliente
                enviarMensaje(clienteSocket, "Bienvenido! Eres el cliente " + contadorClientes);

                System.out.println(); // Línea en blanco para separar
                contadorClientes++;
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar el servidor
            if (servidor != null && !servidor.isClosed()) {
                try {
                    servidor.close();
                    System.out.println("Servidor cerrado correctamente.");
                } catch (IOException e) {
                    System.err.println("Error al cerrar el servidor: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Muestra información detallada sobre la conexión del cliente.
     */
    private static void mostrarInfoCliente(Socket socket, int numeroCliente) {
        System.out.println("[INFO] CLIENTE " + numeroCliente);

        // Puerto local del servidor para esta conexión
        int puertoLocal = socket.getLocalPort();
        System.out.println("Puerto Local (Servidor): " + puertoLocal);

        // Puerto remoto del cliente
        int puertoRemoto = socket.getPort();
        System.out.println("Puerto Remoto (Cliente): " + puertoRemoto);

        // Dirección IP del cliente usando getInetAddress()
        InetAddress direccionCliente = socket.getInetAddress();
        System.out.println("Dirección IP Cliente: " + direccionCliente.getHostAddress());
        System.out.println("Nombre del Host: " + direccionCliente.getHostName());
    }

    /**
     * Envía un mensaje al cliente conectado.
     */
    private static void enviarMensaje(Socket socket, String mensaje) {
        try {
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(mensaje);
        } catch (IOException e) {
            System.err.println("Error al enviar mensaje al cliente: " + e.getMessage());
        }
    }
}