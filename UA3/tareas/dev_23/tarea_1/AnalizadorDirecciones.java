
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class AnalizadorDirecciones {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;

        System.out.println("Analizador de Direcciones IP/URL");
        System.out.println("Introduce una URL o IP (escribe 'localhost' para salir):");

        while (true) {
            System.out.print("\nEscribe la URl o IP: ");
            entrada = sc.nextLine().trim();

            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println("Saliendo del programa...");
                break;
            }

            try {
                InetAddress direccion = InetAddress.getByName(entrada);

                System.out.println("Información encontrada:");
                System.out.println("  Nombre del host: " + direccion.getHostName());
                System.out.println("  Dirección IP:    " + direccion.getHostAddress());
                System.out.println("  Nombre oficial:  " + direccion.getCanonicalHostName());

            } catch (UnknownHostException e) {
                System.err.println("Error: No se pudo encontrar el host '" + entrada + "'");
            }
        }

        sc.close();
    }
}