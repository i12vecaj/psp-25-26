
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String urlIntroducida = "";





        while (!urlIntroducida.equalsIgnoreCase("localhost")) { //Mientras que no se escriba localhost entra en el bucle
            System.out.println("Introduce una url, introduce localhost para salir");
            urlIntroducida = sc.nextLine();




            try {

                URL url = new URL(urlIntroducida);
                visualizar(url);
            } catch (MalformedURLException e) {


                if(!urlIntroducida.equalsIgnoreCase("localhost")) { //Uso este condicional para que solo se muestre este mensaje en el caso de que el usuario escriba algo diferente a localhost para que cuando se escriba localhost además de saliendo no se muestre también que falta http:
                    System.out.println("Error: La URL no es válida. Asegúrate de incluir el protocolo (ej: http://).");


                } else {
                    System.out.println("Saliendo del programa");
                }
            }
        }
        sc.close();
    }

    // Método para mostrar la información detallada
    public static void visualizar(URL url) {

        System.out.println("\tURL completa: " + url.toString());
        System.out.println("\tgetProtocol(): " + url.getProtocol());
        System.out.println("\tgetHost(): " + url.getHost());
        System.out.println("\tgetPort(): " + url.getPort());
        System.out.println("\tgetFile(): " + url.getFile());
        System.out.println("\tgetUserInfo(): " + url.getUserInfo());
        System.out.println("\tgetPath(): " + url.getPath());
        System.out.println("\tgetAuthority(): " + url.getAuthority());
        System.out.println("\tgetQuery(): " + url.getQuery());
        System.out.println("\tgetDefaultPort(): "+ url.getDefaultPort());
        System.out.println("==================================================");
    }
}