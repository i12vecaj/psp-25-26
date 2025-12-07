import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Manejador de cliente que se ejecuta en un hilo independiente.
 * Gestiona toda la comunicación con un cliente específico.
 * 
 * Responsabilidades:
 * - Establecer comunicación bidireccional con el cliente
 * - Enviar mensaje de bienvenida
 * - Procesar mensajes entrantes
 * - Retransmitir mensajes a otros clientes
 * - Gestionar desconexión y limpieza de recursos
 */
public class ClientHandler implements Runnable {
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String clientName;
    private int clientId;
    private boolean isConnected;
    
    /**
     * Constructor del manejador de cliente.
     */
    public ClientHandler(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
        this.isConnected = true;
    }
    
    /**
     * Método principal que se ejecuta en el hilo del cliente.
     * Gestiona todo el ciclo de vida de la conexión.
     */
    @Override
    public void run() {
        try {
            // Inicializar streams de entrada/salida
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            // Enviar mensaje de bienvenida
            sendWelcomeMessage();
            
            // Solicitar y leer nombre del cliente
            out.println("Por favor, ingrese su nombre de usuario:");
            clientName = in.readLine();
            
            // Validar nombre
            if (clientName == null || clientName.trim().isEmpty()) {
                clientName = "Usuario" + clientId;
            } else {
                clientName = clientName.trim();
            }
            
            // Confirmar registro
            out.println("");
            out.println("Bienvenido, " + clientName);
            out.println("Su ID de sesion es: " + clientId);
            out.println("Puede comenzar a enviar mensajes.");
            out.println("Escriba 'exit' o 'salir' para desconectarse.");
            out.println("========================================");
            out.println("");
            
            log("Usuario autenticado: " + clientName);
            
            // Notificar a otros clientes
            String joinMessage = formatSystemMessage(clientName + " se ha conectado al servidor");
            MultiThreadedServer.broadcast(joinMessage, this);
            
            // Bucle principal: leer mensajes del cliente
            String message;
            while (isConnected && (message = in.readLine()) != null) {
                message = message.trim();
                
                // Verificar comando de salida
                if (message.equalsIgnoreCase("exit") || message.equalsIgnoreCase("salir")) {
                    break;
                }
                
                // Ignorar mensajes vacíos
                if (message.isEmpty()) {
                    continue;
                }
                
                // Procesar mensaje
                processMessage(message);
            }
            
        } catch (IOException e) {
            logError("Error de comunicación con cliente: " + e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Procesa un mensaje recibido del cliente.
     */
    private void processMessage(String message) {
        log("Mensaje recibido: " + message);
        
        // Eco al remitente
        String echoMessage = formatEchoMessage(message);
        sendMessage(echoMessage);
        
        // Broadcast a otros clientes
        String broadcastMessage = formatChatMessage(clientName, message);
        MultiThreadedServer.broadcast(broadcastMessage, this);
    }
    
    /**
     * Envía un mensaje a este cliente.
     * Método sincronizado para evitar escrituras concurrentes.
     */
    public synchronized void sendMessage(String message) {
        if (out != null && isConnected) {
            out.println(message);
        }
    }
    
    /**
     * Envía el mensaje de bienvenida al cliente.
     */
    private void sendWelcomeMessage() {
        out.println("========================================");
        out.println("    SERVIDOR DE CHAT SEGURO v1.0       ");
        out.println("========================================");
        out.println("Conexion establecida correctamente");
        out.println("Servidor: localhost:8080");
        out.println("Timestamp: " + getCurrentTimestamp());
        out.println("----------------------------------------");
        out.println("");
    }
    
    /**
     * Gestiona la desconexión del cliente y limpieza de recursos.
     */
    private void disconnect() {
        isConnected = false;
        
        try {
            // Mensaje de despedida
            if (out != null) {
                out.println("");
                out.println("========================================");
                out.println("       DESCONEXION DEL SERVIDOR        ");
                out.println("========================================");
                out.println("Gracias por usar el servicio");
                out.println("Sesion finalizada: " + getCurrentTimestamp());
                out.println("========================================");
            }
            
            log("Desconectando cliente: " + clientName);
            
            // Notificar a otros clientes
            if (clientName != null) {
                String leaveMessage = formatSystemMessage(clientName + " se ha desconectado");
                MultiThreadedServer.broadcast(leaveMessage, this);
            }
            
            // Cerrar recursos
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            
            // Remover de lista de clientes activos
            MultiThreadedServer.removeClient(this);
            
            log("Cliente desconectado exitosamente");
            
        } catch (IOException e) {
            logError("Error al cerrar recursos: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si el cliente está conectado.
     */
    public boolean isConnected() {
        return isConnected && socket != null && !socket.isClosed();
    }
    
    // Métodos de formateo de mensajes
    
    private String formatChatMessage(String username, String message) {
        return "[" + getCurrentTimestamp() + "] <" + username + "> " + message;
    }
    
    private String formatSystemMessage(String message) {
        return "[" + getCurrentTimestamp() + "] [SISTEMA] " + message;
    }
    
    private String formatEchoMessage(String message) {
        return "[" + getCurrentTimestamp() + "] [ENVIADO] " + message;
    }
    
    private String getCurrentTimestamp() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    
    // Métodos de logging
    
    private void log(String message) {
        System.out.println("[CLIENT-" + clientId + " " + getCurrentTimestamp() + "] " + message);
    }
    
    private void logError(String message) {
        System.err.println("[CLIENT-" + clientId + " " + getCurrentTimestamp() + "] ERROR: " + message);
    }
}