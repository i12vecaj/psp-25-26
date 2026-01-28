import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Tarea2Clientes {
    private static final String SERVER_ADDRESS = "10.2.0.15";
    private static final int SERVER_PORT = 6000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("=== Cliente TCP ===");
        System.out.println("Dirección del servidor: " + SERVER_ADDRESS);
        System.out.println("Puerto del servidor: " + SERVER_PORT);
        System.out.println("-----------------------------------");

        while (true) {
            System.out.print("\nIntroduce 'connect' para conectarte al servidor o 'exit' para salir: ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Saliendo del programa.");
                break;
            } else if (input.equalsIgnoreCase("connect")) {
                conectarAlServidor();
            } else {
                System.out.println("Comando no reconocido. Por favor, introduce 'connect' o 'exit'.");
            }
        }

        scanner.close();
    }
    
    private static void conectarAlServidor() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            InetAddress remoteAddress = socket.getInetAddress();
            
            System.out.println("\n=== Conexión establecida ===");
            System.out.println("Información del socket local:");
            System.out.println("  - Puerto local: " + socket.getLocalPort());
            System.out.println("  - Dirección local: " + socket.getLocalAddress().getHostAddress());
            System.out.println("\nInformación del socket remoto:");
            System.out.println("  - Puerto remoto: " + socket.getPort());
            System.out.println("  - Dirección IP remota: " + remoteAddress.getHostAddress());
            System.out.println("  - Nombre del host remoto: " + remoteAddress.getHostName());
            System.out.println("-----------------------------------");
            
        } catch (UnknownHostException e) {
            System.out.println("ERROR: No se pudo resolver la dirección del servidor: " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.out.println("ERROR al conectar con el servidor: " + e.getMessage());
            System.out.println("Asegúrate de que el servidor esté ejecutándose.");
        }
    }
}