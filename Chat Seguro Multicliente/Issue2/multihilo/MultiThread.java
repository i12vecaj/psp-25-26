import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThread {

    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en el puerto " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " +
                        clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
