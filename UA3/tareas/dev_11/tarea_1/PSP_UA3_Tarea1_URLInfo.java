import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;

public class PSP_UA3_Tarea1_URLInfo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;

        System.out.println("Introduce una IP o URL (escribe 'localhost' para salir):");

        do {
            entrada = sc.nextLine();

            if (!entrada.equalsIgnoreCase("localhost")) {
                try {
                    // Si es una URL
                    if (entrada.startsWith("http://") || entrada.startsWith("https://")) {
                        URL url = new URL(entrada);

                        System.out.println("URL completa: " + url.toString());
                        System.out.println("Protocolo: " + url.getProtocol());
                        System.out.println("Host: " + url.getHost());
                        System.out.println("Puerto: " + (url.getPort() == -1 ? "Por defecto" : url.getPort()));
                        System.out.println("Ruta: " + url.getPath());

                        InetAddress ip = InetAddress.getByName(url.getHost());
                        System.out.println("IP asociada: " + ip.getHostAddress());
                    } 
                    // Si es una IP o nombre de dominio
                    else {
                        InetAddress direccion = InetAddress.getByName(entrada);

                        System.out.println("Nombre del host: " + direccion.getHostName());
                        System.out.println("Dirección IP: " + direccion.getHostAddress());
                        System.out.println("¿Es alcanzable?: " + direccion.isReachable(3000));
                    }

                } catch (Exception e) {
                    System.out.println("Error: dirección no válida");
                }

                System.out.println("\nIntroduce otra IP o URL (localhost para salir):");
            }

        } while (!entrada.equalsIgnoreCase("localhost"));

        System.out.println("Programa finalizado.");
        sc.close();
    }
}
