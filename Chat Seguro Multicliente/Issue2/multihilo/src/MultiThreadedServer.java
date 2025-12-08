import java.io.*;
import java.net.*;

public class MultiThreadedServer {

    private ServerSocket serverSocket; // Aquí guardo el socket del servidor

    public MultiThreadedServer(int port) throws IOException {
        // Inicio el servidor en el puerto que yo quiero
        serverSocket = new ServerSocket(port);
        System.out.println("Servidor escuchando en el puerto " + port);
    }

    public void start() throws IOException {
        // Aquí dejo el servidor esperando conexiones para siempre
        while (true) {
            Socket clientSocket = serverSocket.accept(); // Cuando entra un cliente lo acepto
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
            // Cada cliente lo mando a un hilo porque así no se bloquea el servidor
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    public static void main(String[] args) {
        try {
            // Arranco el servidor en el puerto 5000
            MultiThreadedServer server = new MultiThreadedServer(5000);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

