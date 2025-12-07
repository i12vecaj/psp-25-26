import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cliente TCP para conectarse al servidor multicliente.
 * 
 * Funcionalidades:
 * - Establece conexión con el servidor
 * - Envía mensajes al servidor
 * - Recibe mensajes del servidor en tiempo real
 * - Utiliza dos hilos: uno para enviar y otro para recibir
 */
public class SimpleClient {
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    private static boolean running = true;
    
    /**
     * Método principal que inicia el cliente.
     */
    public static void main(String[] args) {
        logClient("Iniciando cliente de chat...");
        logClient("Conectando a " + SERVER_HOST + ":" + SERVER_PORT);
        
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            logClient("Conexion establecida correctamente");
            printSeparator();
            
            // Configurar streams de entrada/salida
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            
            // Hilo para recibir mensajes del servidor
            Thread receiveThread = new Thread(() -> {
                receiveMessages(in);
            });
            receiveThread.setName("ReceiveThread");
            receiveThread.setDaemon(true);
            receiveThread.start();
            
            // Esperar un momento para que se muestren los mensajes de bienvenida
            Thread.sleep(500);
            
            // Hilo principal: enviar mensajes al servidor
            sendMessages(out, scanner);
            
            // Detener recepción y esperar
            running = false;
            receiveThread.join(2000);
            
            scanner.close();
            logClient("Cliente finalizado");
            
        } catch (UnknownHostException e) {
            logError("Host desconocido: " + e.getMessage());
        } catch (IOException e) {
            logError("Error de conexion: " + e.getMessage());
        } catch (InterruptedException e) {
            logError("Thread interrumpido: " + e.getMessage());
        }
    }
    
    /**
     * Gestiona el envío de mensajes al servidor.
     * Lee entrada del usuario y la envía por el socket.
     */
    private static void sendMessages(PrintWriter out, Scanner scanner) {
        String userInput;
        
        while (running && scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            
            // Enviar mensaje al servidor
            out.println(userInput);
            
            // Verificar comando de salida
            if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("salir")) {
                running = false;
                break;
            }
        }
    }
    
    /**
     * Gestiona la recepción de mensajes del servidor.
     * Ejecutado en un hilo separado para recepción asíncrona.
     */
    private static void receiveMessages(BufferedReader in) {
        try {
            String serverMessage;
            while (running && (serverMessage = in.readLine()) != null) {
                // Mostrar mensaje del servidor
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            if (running) {
                logError("Error al recibir mensaje: " + e.getMessage());
            }
        }
    }
    
    // Métodos auxiliares para logging
    
    private static void logClient(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println("[CLIENT " + timestamp + "] " + message);
    }
    
    private static void logError(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.err.println("[ERROR " + timestamp + "] " + message);
    }
    
    private static void printSeparator() {
        System.out.println("========================================");
    }
}