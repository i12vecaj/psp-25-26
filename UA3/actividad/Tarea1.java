import java.net.*;
import java.util.Scanner;

public class Tarea1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada;

        while (true) {
            System.out.print("Introduce una URL o IP (localhost para salir): ");
            entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println("Programa finalizado.");
                break;
            }

            try {
                // Si no tiene protocolo, lo añadimos
                if (!entrada.startsWith("http://") && !entrada.startsWith("https://")) {
                    entrada = "http://" + entrada;
                }

                URL url = new URL(entrada);
                InetAddress direccion = InetAddress.getByName(url.getHost());

                System.out.println("Información:");
                System.out.println("Protocolo: " + url.getProtocol());
                System.out.println("Host: " + url.getHost());
                System.out.println("Puerto: " + (url.getPort() == -1 ? "Por defecto" : url.getPort()));
                System.out.println("Ruta: " + url.getPath());
                System.out.println("IP: " + direccion.getHostAddress());
                System.out.println("----------------------------------");

            } catch (MalformedURLException e) {
                System.out.println("URL no válida.");
            } catch (UnknownHostException e) {
                System.out.println("No se pudo resolver el host.");
            }
        }

        scanner.close();
    }
}
