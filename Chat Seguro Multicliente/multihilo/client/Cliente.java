package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Cliente TCP para conectarse al servidor multicliente
 */
public class Cliente {

    /**
     * Lista de puertos:
     * 8079 -> Version Antigua
     * 8082 -> Version Inet (la ideal)
     * 8081 -> Version Atomic
     * 8080 -> Version Básica
     */

    private static final String HOST = "localhost";
    private static final int PUERTO = 8082;

    public static void main(String[] args) {
        System.out.println("Conectando al servidor...\n");

        try {
            Socket socket = new Socket(HOST, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Conectado al servidor!\n");

            // Hilo para recibir mensajes del servidor
            Thread receptor = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        System.out.println(mensaje);
                    }
                } catch (IOException e) {
                    System.out.println("Conexion cerrada");
                }
            });
            receptor.start();

            // Enviar mensajes al servidor
            String mensaje;
            while (scanner.hasNextLine()) {
                mensaje = scanner.nextLine();
                salida.println(mensaje);

                if (mensaje.equalsIgnoreCase("salir")) {
                    break;
                }
            }

            socket.close();
            scanner.close();

        } catch (IOException e) {
            System.err.println("Error de conexion: " + e.getMessage());
            System.out.println("Tal vez el servidor esté cerrado o el puerto no sea el correcto.");
        }

        System.out.println("\nDesconectado del servidor");
    }
}