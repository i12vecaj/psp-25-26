import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class tarea1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Introduce una URL o IP (escribe 'localhost' para salir):");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("localhost")) {
                System.out.println("Programa finalizado.");
                break;
            }

            try {
                InetAddress inet = InetAddress.getByName(input);

                System.out.println("Información de: " + input);
                System.out.println("Nombre del host: " + inet.getHostName());
                System.out.println("Dirección IP: " + inet.getHostAddress());
                System.out.println("¿Reachable? " + inet.isReachable(2000) + " (2 segundos timeout)");
                System.out.println("--------------------------------------");
            } catch (UnknownHostException e) {
                System.out.println("No se pudo resolver la URL/IP: " + input);
            } catch (Exception e) {
                System.out.println("Error al comprobar la dirección: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
