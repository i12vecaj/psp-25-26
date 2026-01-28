import java.net.*;
import java.util.Scanner;

/**
 * Programa básico que consulta información de URLs o IPs usando InetAddress.
 * Continúa hasta que el usuario introduce "localhost".
 */
public class tarea1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada = "";

        System.out.println("Programa de consulta de URLs/IPs");
        System.out.println("Escribe 'localhost' para salir\n");

        // Bucle hasta que el usuario introduce "localhost"
        while (!entrada.equalsIgnoreCase("localhost")) {
            System.out.print("Introduce URL o IP: ");
            entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                continue;
            }

            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println("Programa finalizado.");
                break;
            }

            mostrarInformacion(entrada);
        }

        scanner.close();
    }

    private static void mostrarInformacion(String url) {
        try {
            url = limpiarURL(url);

            // Obtener información con InetAddress
            InetAddress direccion = InetAddress.getByName(url);

            System.out.println("\n--- Información de: " + url + " ---");
            System.out.println("Nombre del host: " + direccion.getHostName());
            System.out.println("Nombre canonico: " + direccion.getCanonicalHostName());
            System.out.println("Dirección IP: " + direccion.getHostAddress());
            System.out.println("Está caída?: " + !direccion.isReachable(5000));
            System.out.println();

        } catch (UnknownHostException e) {
            System.out.println("ERROR: No se pudo resolver " + url);
            System.out.println();
        } catch (Exception e) {
            System.out.println("ERROR: No se pudo conectar a " + url);
            System.out.println();
        }
    }

    /**
     * Elimina https://, http://, etc. de una URL (función para facilitar la
     * lectura)
     */
    private static String limpiarURL(String url) {
        // Eliminar https://
        if (url.startsWith("https://")) {
            url = url.substring(8);
        }
        // Eliminar http://
        else if (url.startsWith("http://")) {
            url = url.substring(7);
        }

        // Eliminar la ruta si existe (todo después de la primera /)
        int index = url.indexOf('/');
        if (index != -1) {
            url = url.substring(0, index);
        }

        return url;
    }
}
