package server;

import java.io.*;
import java.net.*;

/**
 * Versión ANTIGUA del servidor.
 * Esto es de cuando empecé a hacerlo.
 * CARACTERÍSTICAS:
 * - Multihilo (acepta múltiples clientes).
 * - NO hay comunicación entre clientes (NO Broadcast).
 * - Solo hace "Echo" (repite lo que el cliente dice).
 * - La generación de IDs es básica (static int).
 */
public class MultiThreadedServerAntiguo {

    private static final int PORT = 8079; // Puerto distinto para no chocar

    private static int clienteId = 0;

    public static void main(String[] args) {
        System.out.println("Servidor ANTIGUO iniciado en puerto " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                System.out.println("Esperando clientes...");
                Socket clientSocket = serverSocket.accept();
                clienteId++;
                System.out.println("Nuevo cliente conectado (ID " + clienteId + "): " + clientSocket.getInetAddress());

                // Crear y arrancar un nuevo hilo para el cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket, clienteId);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clase interna que maneja cada cliente en un hilo separado
     */
    static class ClientHandler implements Runnable {

        private Socket clientSocket;
        private int id;

        public ClientHandler(Socket socket, int id) {
            this.clientSocket = socket;
            this.id = id;
        }

        @Override
        public void run() {
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                // Mensaje de bienvenida
                out.println("Bienvenido al Servidor Versión Antigua!");
                out.println("Eres el cliente #" + id + "\n");
                out.println("Escribe 'salir' para desconectar.\n");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if ("salir".equalsIgnoreCase(inputLine)) {
                        out.println("Hasta luego!");
                        break;
                    }

                    // Echo del mensaje
                    System.out.println("Cliente #" + id + inputLine);
                    out.println("Mensaje enviado: " + inputLine);
                }

            } catch (IOException e) {
                System.err.println("Error manejando cliente #" + id + ": " + e.getMessage());
            } finally {
                try {
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                    System.out.println("Cliente #" + id + " desconectado.");
                } catch (IOException e) {
                    System.err.println("Error cerrando socket: " + e.getMessage());
                }
            }
        }
    }
}
