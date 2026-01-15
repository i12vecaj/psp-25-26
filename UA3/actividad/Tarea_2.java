import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Tarea_2 {
    public static void main(String[] args) {
        final int PORT = 12345; // Puerto del servidor

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor TCP escuchando en el puerto " + PORT);

            for (int i = 0; i < 2; i++) { // Aceptar dos clientes
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Mostrar puertos local y remoto
                System.out.println("Puerto local: " + clientSocket.getLocalPort());
                System.out.println("Puerto remoto: " + clientSocket.getPort());
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
