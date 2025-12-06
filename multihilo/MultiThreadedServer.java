import java.io.*;
import java.net.*;

public class MultiThreadedServer {
    private ServerSocket serverSocket; //El puerto donde el servidor


    public MultiThreadedServer(int port) throws IOException {
        serverSocket = new ServerSocket(port); //se abre el puerto
        System.out.println("Servidor escuchando en puerto " + port);
    }

    // Método que arranca el servidor y acepta conexiones
    public void start() {
        while (true) { //siempre esperando clientes
            try {
                //Espera a que un cliente se conecte
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                //se crea un manejador para ese cliente
                GestionHilo handler = new GestionHilo(clientSocket);

                //Cada cliente se atiende en un hilo independiente
                new Thread(handler).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
