import java.io.IOException;
import java.net.Socket;

public class ClienteTCP {

    public static void main(String[] args) {
        String servidor = "localhost"; // o IP del servidor
        int puertoServidor = 5000;

        try (Socket socket = new Socket(servidor, puertoServidor)) {
            System.out.println("Conectado al servidor " + servidor + " en el puerto " + puertoServidor);
            System.out.println("Puerto local del cliente: " + socket.getLocalPort());
            System.out.println("Puerto remoto del servidor: " + socket.getPort());
            System.out.println("Dirección IP del servidor: " + socket.getInetAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
