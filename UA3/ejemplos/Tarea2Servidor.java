import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Tarea2Servidor {
    private static final int PORT = 6000;
    private static final int MAX_CLIENTS = 2;
    private static ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTS);
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("=== Servidor TCP iniciado ===");
            System.out.println("Escuchando en puerto: " + PORT);
            System.out.println("Esperando " + MAX_CLIENTS + " clientes...");
            System.out.println("-----------------------------------\n");
            
            // Aceptar hasta MAX_CLIENTS
            for (int i = 1; i <= MAX_CLIENTS; i++) {
                System.out.println("Esperando cliente " + i + "...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente " + i + " conectado!");
                
                // Crear un nuevo hilo para manejar este cliente
                executor.execute(new ClienteHandler(clientSocket, i));
            }
            
            System.out.println("\n=== Todos los clientes conectados ===");
            System.out.println("Servidor esperando mensajes...");
            
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
    
    static class ClienteHandler implements Runnable {
        private Socket socket;
        private int numeroCliente;
        
        public ClienteHandler(Socket socket, int numeroCliente) {
            this.socket = socket;
            this.numeroCliente = numeroCliente;
        }
        
        @Override
        public void run() {
            try {
                // Obtener información de conexión
                InetAddress clientAddress = socket.getInetAddress();
                
                System.out.println("\n=== Información Cliente " + numeroCliente + " ===");
                System.out.println("IP: " + clientAddress.getHostAddress());
                System.out.println("Puerto cliente: " + socket.getPort());
                System.out.println("Puerto servidor: " + socket.getLocalPort());
                System.out.println("----------------------------");
                
                // Crear streams para comunicación
                BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true);
                
                // Enviar mensaje de bienvenida al cliente
                output.println("Bienvenido cliente " + numeroCliente + 
                             "! Escribe 'adios' para terminar.");
                
                // Leer mensajes del cliente
                String mensajeCliente;
                while ((mensajeCliente = input.readLine()) != null) {
                    System.out.println("Cliente " + numeroCliente + " dice: " + mensajeCliente);
                    
                    if (mensajeCliente.equalsIgnoreCase("adios")) {
                        output.println("Hasta luego cliente " + numeroCliente + "!");
                        break;
                    }
                    
                    // Responder al cliente
                    System.out.print("Respuesta para cliente " + numeroCliente + ": ");
                    BufferedReader serverInput = new BufferedReader(
                        new InputStreamReader(System.in));
                    String respuesta = serverInput.readLine();
                    output.println("Servidor: " + respuesta);
                }
                
                System.out.println("Cliente " + numeroCliente + " desconectado.");
                socket.close();
                
            } catch (IOException e) {
                System.out.println("Error con cliente " + numeroCliente + ": " + e.getMessage());
            }
        }
    }
}