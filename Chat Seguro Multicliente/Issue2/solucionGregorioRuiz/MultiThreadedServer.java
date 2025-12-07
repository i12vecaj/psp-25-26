import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Servidor TCP multicliente que gestiona conexiones simultáneas mediante hilos.
 * 
 * Funcionalidades:
 * - Acepta múltiples conexiones de clientes en el puerto configurado
 * - Crea un hilo independiente para cada cliente conectado
 * - Mantiene un registro de todos los clientes activos
 * - Permite broadcast de mensajes entre clientes
 */
public class MultiThreadedServer {
    
    // Puerto en el que el servidor escuchará conexiones
    private static final int PORT = 8080;
    
    // Conjunto de manejadores de clientes activos (thread-safe)
    private static Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());
    
    // Contador de clientes para asignar IDs únicos
    private static int clientCounter = 0;
    
    /**
     * Método principal que inicia el servidor y acepta conexiones.
     */
    public static void main(String[] args) {
        logServer("Servidor Multicliente iniciado");
        logServer("Escuchando en puerto: " + PORT);
        logServer("Esperando conexiones...");
        printSeparator();
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Bucle infinito para aceptar conexiones
            while (true) {
                // Bloquea hasta que llega una nueva conexión
                Socket clientSocket = serverSocket.accept();
                clientCounter++;
                
                // Información de la conexión
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                int clientPort = clientSocket.getPort();
                
                logServer("Nueva conexión aceptada");
                logServer("  Dirección: " + clientAddress + ":" + clientPort);
                logServer("  Cliente ID: " + clientCounter);
                
                // Crear manejador de cliente y agregarlo a la lista
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientCounter);
                clientHandlers.add(clientHandler);
                
                // Crear e iniciar un nuevo hilo para este cliente
                Thread thread = new Thread(clientHandler);
                thread.setName("ClientThread-" + clientCounter);
                thread.start();
                
                logServer("Clientes conectados: " + clientHandlers.size());
                printSeparator();
            }
        } catch (IOException e) {
            logError("Error crítico en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Envía un mensaje a todos los clientes excepto al remitente.
     * Método sincronizado para evitar condiciones de carrera.
     */
    public static synchronized void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender && client.isConnected()) {
                client.sendMessage(message);
            }
        }
    }
    
    /**
     * Elimina un cliente de la lista de clientes activos.
     * Método sincronizado para garantizar thread-safety.
     */
    public static synchronized void removeClient(ClientHandler client) {
        clientHandlers.remove(client);
        logServer("Cliente removido. Clientes activos: " + clientHandlers.size());
    }
    
    /**
     * Retorna el número de clientes actualmente conectados.
     */
    public static synchronized int getActiveClients() {
        return clientHandlers.size();
    }
    
    // Métodos auxiliares para logging
    
    private static void logServer(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println("[SERVER " + timestamp + "] " + message);
    }
    
    private static void logError(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.err.println("[ERROR " + timestamp + "] " + message);
    }
    
    private static void printSeparator() {
        System.out.println("----------------------------------------");
    }
}