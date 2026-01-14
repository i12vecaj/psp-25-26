// Tarea 1 UA3
// Alberto Nieto Lozano

/* Crea un programa en Java que admita desde la línea de comandos una URL
y visualice información sobre esta. Modifica el programa para que
admita continuamente nuevas IP o URL y muestre la
información hasta que el usuario inserte "localhost */

import java.net.*;
import java.util.Scanner;

public class InfoURL {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;

        while (true) {
            System.out.println("Introduce una URL o IP: ");
            entrada = sc.nextLine();

            if (entrada.equals("localhost")) {
                break;
            }

            try {
                URL url = new URL(entrada);
                System.out.println("URL completa: " + url.toString());
                System.out.println("Protocolo: " + url.getProtocol());
                System.out.println("Host: " + url.getHost());
                System.out.println("Puerto: " + url.getPort());
                System.out.println("\tgetPath(): " + url.getPath());
                System.out.println("\tgetQuery(): " + url.getQuery());
                System.out.println("----------------------------------");

                InetAddress dir = InetAddress.getByName(url.getHost());
                System.out.println("HostName: " + dir.getHostName());
                System.out.println("HostAddress: " + dir.getHostAddress());
                System.out.println("CanonicalHostName: " + dir.getCanonicalHostName());
                System.out.println("==================================");

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        sc.close();
    }
}
