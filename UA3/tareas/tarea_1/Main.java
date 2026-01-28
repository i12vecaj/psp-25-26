
/*
Crea un programa en Java que admita desde la línea de comandos una URL y visualice información sobre esta.
Modifica el programa para que admita continuamente nuevas IP o URL y muestre la información hasta que el usuario inserte "localhost".
Autor: Bernardo Cubero
Fecha 14/01/2026
 */


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws MalformedURLException {

        Scanner teclado = new Scanner(System.in);
        String host = " ";
        do {
            System.out.println("Ingresa una direcion solo introduce la dirección www. ");
            host = teclado.nextLine();
            //Si introduce localhost se sale del do while
            if (host.equals("localhost")) {
                System.out.println("Programa finalizado.");
                break;
            }
            //Formateamos el host para que solo tenga el cliente que introducir www.direccion.com
            if (!host.startsWith("http://") && !host.startsWith("https://")) {
                host = "http://" + host;
            }
            //Control de errores
            try {
                URL url = new URL(host);
                visualizar(url);
            } catch (MalformedURLException e) {
                System.out.println("Error: El host no es valido");
            }
        } while (!host.equals("localhost"));
    }

    //Metodos para comprobar
    private static void visualizar(URL url) {
        System.out.println("URL completa: " + url.toString());
        System.out.println("Host: " + url.getHost());
        System.out.println("Port: " + url.getPort());
        System.out.println("Protocol: " + url.getProtocol());
        System.out.println("Path: " + url.getPath());
        System.out.println("Query: " + url.getQuery());

    }
}

