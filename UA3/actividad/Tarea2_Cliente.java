import java.net.Socket;
import java.io.IOException;

public class Tarea2_Cliente {

    //el numero de clientes necesario, se deben conectar en dispositivos distintos con el mismo codigo

    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 5000;

        try (Socket socket = new Socket(servidor, puerto)) {

            System.out.println("Conectado al servidor.");
            System.out.println("Puerto local del cliente: " + socket.getLocalPort());
            System.out.println("Puerto remoto del servidor: " + socket.getPort());
            System.out.println("IP del servidor remoto: " + socket.getInetAddress().getHostAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

