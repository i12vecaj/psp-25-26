package Tarea1;

import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;

public class tarea1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada;

        System.out.println("Introduce una URL o IP (escribe 'localhost' para salir):");

        while (true) {
            entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("localhost")) {
                System.out.println("Programa finalizado.");
                break;
            }

            try {
                if (!entrada.startsWith("http://") && !entrada.startsWith("https://")) {
                    entrada = "http://" + entrada;
                }

                URL url = new URL(entrada);
                InetAddress direccion = InetAddress.getByName(url.getHost());

                System.out.println("----- Información -----");
                System.out.println("Protocolo: " + url.getProtocol());
                System.out.println("Host: " + url.getHost());
                System.out.println("Puerto: " + (url.getPort() == -1 ? "Puerto por defecto" : url.getPort()));
                System.out.println("Ruta: " + url.getPath());
                System.out.println("Nombre del host: " + direccion.getHostName());
                System.out.println("Dirección IP: " + direccion.getHostAddress());
                System.out.println("-----------------------");

            } catch (Exception e) {
                System.out.println("Error: URL o IP no válida.");
            }

            System.out.println("\nIntroduce otra URL o IP ('localhost' para salir):");
        }

        scanner.close();
    }
}

