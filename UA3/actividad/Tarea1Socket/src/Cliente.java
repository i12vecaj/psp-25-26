/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 14/01/2026
 * Título: Tarea 1 - Programación de Sockets 1
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        String host = "localhost"; // Dirección del servidor
        int puerto = 5000;         // Puerto del servidor

        try {
            // Se conecta el cliente con el servidor
            Socket socket = new Socket(host, puerto);

            // Flujo para recibir datos del servidor
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // Flujo para enviar datos al servidor
            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Introduce una URL o IP ('localhost' para salir):");

            String texto;

            // Bucle para enviar URLs al servidor
            while (true) {
                texto = scanner.nextLine();
                salida.println(texto);

                // Si el usuario escribe localhost se termina el programa
                if (texto.equalsIgnoreCase("localhost")) {
                    System.out.println(entrada.readLine());
                    break;
                }

                // Se muestra la respuesta del servidor
                String respuesta;
                while (!(respuesta = entrada.readLine()).equals("--------------------------------")) {
                    System.out.println(respuesta);
                }
                System.out.println("--------------------------------");
            }

            // Cierre de recursos
            scanner.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
