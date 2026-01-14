import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class AnalizadorURL {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada = "";

        System.out.println("=== Analizador de URLs ===");
        System.out.println("Introduce una URL completa (ej: https://www.google.com:80/search?q=java)");
        System.out.println("Escribe 'localhost' para salir.\n");

        while (true) {
            System.out.print("Introduce URL > ");
            entrada = scanner.nextLine();

            // Condición de salida (ignorando mayúsculas/minúsculas)
            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println("Finalizando programa...");
                break;
            }

            try {
                // Creamos el objeto URL
                URL url = new URL(entrada);

                System.out.println("\n--- Información de la URL ---");
                System.out.println("URL Completa: " + url.toString());
                System.out.println("Protocolo:    " + url.getProtocol());
                System.out.println("Autoridad:    " + url.getAuthority());
                System.out.println("Host:         " + url.getHost());
                
                // El puerto devuelve -1 si no está especificado en la URL
                int puerto = url.getPort();
                System.out.println("Puerto:       " + (puerto == -1 ? "Por defecto" : puerto));
                
                System.out.println("Ruta (Path):  " + url.getPath());
                System.out.println("Consulta:     " + url.getQuery());
                System.out.println("Archivo:      " + url.getFile());
                System.out.println("Referencia:   " + url.getRef());
                System.out.println("-----------------------------\n");

            } catch (MalformedURLException e) {
                // Capturamos si la URL no tiene formato válido (ej: falta http://)
                System.out.println("ERROR: Formato de URL inválido.");
                System.out.println("Asegúrate de incluir el protocolo (ej: http:// o https://).\n");
            }
        }
        
        scanner.close();
    }
}
