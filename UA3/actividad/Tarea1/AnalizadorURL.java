import java.net.*;
import java.util.Scanner;
import java.io.IOException;

public class AnalizadorURL {

    public static void main(String[] args) {
        mostrarBanner();

        Scanner scanner = new Scanner(System.in);

        if (args.length > 0) {
            String urlInicial = args[0];
            System.out.println("===============================================================");
            System.out.println("Analizando URL desde argumentos de linea de comandos...");
            System.out.println("===============================================================");
            analizarURL(urlInicial);
            System.out.println();
        }

        modoInteractivo(scanner);

        scanner.close();
    }

    private static void mostrarBanner() {
        System.out.println("===============================================================");
        System.out.println("          ANALIZADOR DE URLs E IPs - VERSION 2.0              ");
        System.out.println("===============================================================");
        System.out.println();
    }

    private static void modoInteractivo(Scanner scanner) {
        System.out.println("MODO INTERACTIVO ACTIVADO");
        System.out.println("Ingresa URLs o IPs para analizar.");
        System.out.println("Escribe 'localhost' para salir del programa.");
        System.out.println("---------------------------------------------------------------");
        System.out.println();

        int contador = 1;

        while (true) {
            System.out.print("Ingresa URL/IP [" + contador + "]: ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println();
                System.out.println("===============================================================");
                System.out.println("Usuario ingreso 'localhost' - Finalizando programa...");
                System.out.println("===============================================================");
                System.out.println("Gracias por usar el Analizador de URLs!");
                break;
            }

            if (entrada.isEmpty()) {
                System.out.println("Error: Debes ingresar una URL o IP valida.");
                System.out.println();
                continue;
            }

            System.out.println();
            System.out.println("===============================================================");
            System.out.println("Analizando entrada #" + contador + ": " + entrada);
            System.out.println("===============================================================");

            analizarURL(entrada);

            System.out.println();
            contador++;
        }
    }

    private static void analizarURL(String urlString) {
        try {
            URL url;

            if (!urlString.contains("://")) {
                System.out.println("No se detecto protocolo, usando HTTP por defecto...");
                urlString = "http://" + urlString;
            }

            url = new URL(urlString);

            System.out.println();
            System.out.println("INFORMACION DE LA URL:");
            System.out.println("---------------------------------------------------------------");

            System.out.println("  URL Completa:      " + url.toString());
            System.out.println("  Protocolo:         " + url.getProtocol());
            System.out.println("  Host:              " + url.getHost());
            System.out.println("  Puerto:            " +
                    (url.getPort() == -1 ? "Por defecto (" + getPuertoDefecto(url.getProtocol()) + ")" : url.getPort()));
            System.out.println("  Ruta:              " +
                    (url.getPath().isEmpty() ? "/" : url.getPath()));

            if (url.getQuery() != null && !url.getQuery().isEmpty()) {
                System.out.println("  Query String:      " + url.getQuery());
            }

            if (url.getRef() != null && !url.getRef().isEmpty()) {
                System.out.println("  Ancla (Fragment):  " + url.getRef());
            }

            if (url.getUserInfo() != null) {
                System.out.println("  Usuario Info:      " + url.getUserInfo());
            }

            System.out.println("  Autoridad:         " + url.getAuthority());
            System.out.println("  Archivo:           " +
                    (url.getFile().isEmpty() ? "/" : url.getFile()));

            System.out.println();
            System.out.println("RESOLUCION DNS:");
            System.out.println("---------------------------------------------------------------");

            try {
                InetAddress direccion = InetAddress.getByName(url.getHost());
                System.out.println("  Host resuelto correctamente");
                System.out.println("  Direccion IP:      " + direccion.getHostAddress());
                System.out.println("  Nombre Canonico:   " + direccion.getCanonicalHostName());
                System.out.println("  Nombre del Host:   " + direccion.getHostName());

                InetAddress[] todasDirecciones = InetAddress.getAllByName(url.getHost());
                if (todasDirecciones.length > 1) {
                    System.out.println("  Multiples IPs:     " + todasDirecciones.length + " direcciones encontradas");
                    for (int i = 0; i < todasDirecciones.length; i++) {
                        System.out.println("     IP " + (i + 1) + ":            " +
                                todasDirecciones[i].getHostAddress());
                    }
                }

                if (direccion.isLoopbackAddress()) {
                    System.out.println("  Es direccion loopback (localhost)");
                }

                if (direccion.isSiteLocalAddress()) {
                    System.out.println("  Es direccion privada/local");
                }

                if (direccion.isMulticastAddress()) {
                    System.out.println("  Es direccion multicast");
                }

            } catch (UnknownHostException e) {
                System.out.println("  No se pudo resolver el host: " + e.getMessage());
            }

            System.out.println();
            System.out.println("ANALISIS DE CONECTIVIDAD:");
            System.out.println("---------------------------------------------------------------");

            try {
                InetAddress direccion = InetAddress.getByName(url.getHost());

                System.out.print("  Probando conectividad (timeout 3s)...");
                boolean alcanzable = direccion.isReachable(3000);

                if (alcanzable) {
                    System.out.println(" Host ALCANZABLE");
                } else {
                    System.out.println(" Host NO ALCANZABLE");
                    System.out.println("     (Nota: Algunos hosts bloquean ICMP)");
                }

            } catch (IOException e) {
                System.out.println("  Error al verificar conectividad: " + e.getMessage());
            }

            System.out.println();
            System.out.println("INFORMACION ADICIONAL:");
            System.out.println("---------------------------------------------------------------");

            String tipoProtocolo = clasificarProtocolo(url.getProtocol());
            System.out.println("  Tipo de protocolo: " + tipoProtocolo);

            String seguridad = esSeguro(url.getProtocol()) ? "Seguro (cifrado)" : "No seguro (sin cifrado)";
            System.out.println("  Seguridad:         " + seguridad);

            System.out.println("  URL Externa:       " + url.toExternalForm());

        } catch (MalformedURLException e) {
            System.out.println();
            System.out.println("ERROR: URL mal formada");
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println();
            System.out.println("Consejos:");
            System.out.println("   - Verifica que la URL tenga el formato correcto");
            System.out.println("   - Ejemplos validos:");
            System.out.println("     * http://www.ejemplo.com");
            System.out.println("     * https://ejemplo.com/ruta");
            System.out.println("     * ftp://192.168.1.1");
            System.out.println("     * www.google.com (se agregara http://)");
        }
    }

    private static int getPuertoDefecto(String protocolo) {
        switch (protocolo.toLowerCase()) {
            case "http":
                return 80;
            case "https":
                return 443;
            case "ftp":
                return 21;
            case "ssh":
                return 22;
            case "telnet":
                return 23;
            case "smtp":
                return 25;
            case "dns":
                return 53;
            default:
                return -1;
        }
    }

    private static String clasificarProtocolo(String protocolo) {
        switch (protocolo.toLowerCase()) {
            case "http":
            case "https":
                return "Web (HTTP/HTTPS)";
            case "ftp":
            case "ftps":
                return "Transferencia de archivos (FTP)";
            case "file":
                return "Sistema de archivos local";
            case "mailto":
                return "Correo electronico";
            case "ssh":
                return "Shell seguro (SSH)";
            case "telnet":
                return "Terminal remoto";
            case "jdbc":
                return "Base de datos (JDBC)";
            default:
                return "Protocolo: " + protocolo.toUpperCase();
        }
    }

    private static boolean esSeguro(String protocolo) {
        String protocoloLower = protocolo.toLowerCase();
        return protocoloLower.equals("https") ||
                protocoloLower.equals("ftps") ||
                protocoloLower.equals("ssh") ||
                protocoloLower.equals("sftp");
    }
}