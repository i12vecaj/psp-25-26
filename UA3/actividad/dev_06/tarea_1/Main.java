import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;

        System.out.println("--- Analizador de Direcciones de Red ---");
        System.out.println("Introduce una URL o IP (escribe 'localhost' para salir):");

        while (true) {
            System.out.print("\nEntrada: ");
            entrada = sc.nextLine();

            // Condición de salida
            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println("Saliendo del programa...");
                break;
            }

            mostrarInfo(entrada);
        }
        sc.close();
    }

    public static void mostrarInfo(String host) {
        try {
            // Obtener información del host
            InetAddress direccion = InetAddress.getByName(host);

            System.out.println("Información encontrada:");
            System.out.println("Nombre del host: " + direccion.getHostName().toString());
            System.out.println("Dirección IP: " + direccion.getHostAddress().toString());


        } catch (UnknownHostException e) {
            System.err.println("Error: No se pudo encontrar la dirección para: " + host);
        }
    }
}