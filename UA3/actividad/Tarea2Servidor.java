import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

public class Tarea2Servidor {
    private static final int PORT = 6000;
    private static final int MAX_CLIENTS = 2;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor TCP escuchando en el puerto " + PORT);
            
            // Contador para clientes conectados
            int clientesConectados = 0;
            
            while (clientesConectados < MAX_CLIENTS) {
                System.out.println("\nEsperando cliente " + (clientesConectados + 1) + " de " + MAX_CLIENTS + "...");
                Socket clientSocket = serverSocket.accept();
                clientesConectados++;
                
                // Mostrar información del cliente conectado
                mostrarInformacionCliente(clientSocket, clientesConectados);
                
                // Cerrar el socket del cliente después de mostrar la información
                clientSocket.close();
            }
            
            System.out.println("\nSe han conectado los " + MAX_CLIENTS + " clientes. Servidor finalizado.");
            
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
    
    private static void mostrarInformacionCliente(Socket clientSocket, int numeroCliente) {
        try {
            InetAddress remoteAddress = clientSocket.getInetAddress();
            
            System.out.println("=== Cliente " + numeroCliente + " conectado ===");
            System.out.println("Puerto local del servidor (con este cliente): " + clientSocket.getLocalPort());
            System.out.println("Puerto remoto del cliente: " + clientSocket.getPort());
            System.out.println("Dirección IP del cliente: " + remoteAddress.getHostAddress());
            System.out.println("Nombre del host del cliente: " + remoteAddress.getHostName());
            
        } catch (Exception e) {
            System.out.println("Error al obtener información del cliente: " + e.getMessage());
        }
    }
}