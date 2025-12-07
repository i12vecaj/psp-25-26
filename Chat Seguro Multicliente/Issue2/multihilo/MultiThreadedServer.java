package Issue2.multihilo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {

    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en el puerto " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + clientSocket.getInetAddress());

                // Crear un hilo para cada cliente
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
