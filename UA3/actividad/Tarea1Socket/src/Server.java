/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 14/01/2026
 * Título: Tarea 1 - Programación de Sockets 1
 */

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {

        int puerto = 5000; // Puerto

        try {
            // Se crea el servidor
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);

            // Espera a que se conecte un cliente
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado");

            // Flujo de entrada
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(cliente.getInputStream()));

            // Flujo de salida
            PrintWriter salida = new PrintWriter(
                    cliente.getOutputStream(), true);

            String mensaje;

            // Bucle para recibir URLs continuamente
            while ((mensaje = entrada.readLine()) != null) {

                // Si el cliente escribe localhost se cierra la conexión
                if (mensaje.equalsIgnoreCase("localhost")) {
                    salida.println("Conexión cerrada por el cliente.");
                    break;
                }

                try {
                    // Si no tiene protocolo se le añade http
                    if (!mensaje.startsWith("http://") && !mensaje.startsWith("https://")) {
                        mensaje = "http://" + mensaje;
                    }

                    // Se crea el objeto URL
                    URL url = new URL(mensaje);

                    // Se envía la información de la URL al cliente
                    salida.println("----- Información de la URL -----");
                    salida.println("Protocolo: " + url.getProtocol());
                    salida.println("Host: " + url.getHost());
                    salida.println("Puerto: " +
                            (url.getPort() == -1 ? "Puerto por defecto" : url.getPort()));
                    salida.println("Ruta: " + url.getPath());
                    salida.println("Archivo: " + url.getFile());
                    salida.println("Referencia: " + url.getRef());
                    salida.println("--------------------------------");

                } catch (MalformedURLException e) {
                    // Si la URL está mal escrita
                    salida.println("URL no válida.");
                    salida.println("--------------------------------");
                }
            }

            // Cierre de conexiones
            cliente.close();
            servidor.close();
            System.out.println("Servidor cerrado.");

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
