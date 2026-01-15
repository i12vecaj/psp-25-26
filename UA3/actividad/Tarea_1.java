import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Tarea_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Introduce una URL o IP (o 'localhost' para salir): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("localhost")) {
                System.out.println("Saliendo del programa.");
                break;
            }

            try {
                InetAddress address = InetAddress.getByName(input);
                System.out.println("Información sobre: " + input);
                System.out.println("Host Name: " + address.getHostName());
                System.out.println("Host Address: " + address.getHostAddress());
                System.out.println("Is Reachable: " + address.isReachable(5000));
            } catch (UnknownHostException e) {
                System.out.println("No se pudo resolver la dirección: " + input);
            } catch (Exception e) {
                System.out.println("Ocurrió un error al intentar obtener la información.");
            }

            System.out.println();
        }

        scanner.close();
    }
}