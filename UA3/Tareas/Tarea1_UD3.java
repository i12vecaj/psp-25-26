import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class InformacionRed {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada = "";

        System.out.println("--- Visualizador de Información de Red ---");
        
        while (true) {
            System.out.print("\nIntroduce una URL o IP (o 'localhost' para salir): ");
            entrada = sc.nextLine();

            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println("Saliendo del programa...");
                break;
            }

            try {
                // Obtener información del host
                InetAddress direccion = InetAddress.getByName(entrada);
                
                System.out.println("Información obtenida:");
                System.out.println("  Nombre del Host: " + direccion.getHostName());
                System.out.println("  Dirección IP:    " + direccion.getHostAddress());
                System.out.println("  Nombre Canónico: " + direccion.getCanonicalHostName());
                
            } catch (UnknownHostException e) {
                System.err.println("Error: No se pudo encontrar el host '" + entrada + "'");
            }
        }
        sc.close();
    }
}
