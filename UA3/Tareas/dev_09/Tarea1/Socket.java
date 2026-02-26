import java.net.InetAddress;
import java.util.Scanner;

public class Socket {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada;

        while (true) {
            System.out.println("Introduce una URL o IP (localhost para salir): ");
            entrada = scanner.nextLine();

            if (entrada.equals("localhost")) {
                System.out.println("Adios!");
                break;
            }

            try {
                InetAddress direccion = InetAddress.getByName(entrada);
                System.out.println("Host: " + direccion.getHostName());
                System.out.println("IP: " + direccion.getHostAddress());
                System.out.println("Alcanzable: " + direccion.isReachable(5000));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}