import java.io.*;
import java.net.*;

public class MultiThreadedServer {
    private int port;
    public MultiThreadedServer(int port) { this.port = port; }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en puerto " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexión desde " + clientSocket.getRemoteSocketAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MultiThreadedServer(5000).startServer();
    }
}
