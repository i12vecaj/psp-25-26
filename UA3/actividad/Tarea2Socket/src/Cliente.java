/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 14/01/2026
 * Título: Tarea 2 - Programación de Sockets 2
 */

import java.net.*;
import java.io.*;

public class Cliente {

    public static void main(String[] args) {

        String host = "localhost";
        int puerto = 6000;

        try {
            Socket socket = new Socket(host, puerto);

            System.out.println("Cliente 1 conectado");
            System.out.println("Puerto local del cliente: " + socket.getLocalPort());
            System.out.println("Puerto remoto del servidor: " + socket.getPort());
            System.out.println("IP del servidor: " + socket.getInetAddress().getHostAddress());
            System.out.println("--------------------------------");

            socket.close();

        } catch (IOException e) {
            System.out.println("Error en el cliente 1: " + e.getMessage());
        }
    }
}
