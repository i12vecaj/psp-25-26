/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 14/01/2026
 * Título: Tarea 2 - Programación de Sockets 2
 */

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {

        int puerto = 6000; // Puerto del servidor

        try {
            // Se crea el socket servidor
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);

            // El servidor acepta dos clientes
            for (int i = 1; i <= 2; i++) {

                System.out.println("Esperando al cliente " + i + "...");
                Socket cliente = servidor.accept();

                System.out.println("Cliente " + i + " conectado");

                // Se muestran los puertos del cliente
                System.out.println("Puerto local del servidor: " + cliente.getLocalPort());
                System.out.println("Puerto remoto del cliente: " + cliente.getPort());
                System.out.println("--------------------------------");

                // Se cierra el socket del cliente
                cliente.close();
            }

            // Se cierra el servidor
            servidor.close();
            System.out.println("Servidor cerrado.");

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
