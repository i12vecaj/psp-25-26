import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int Puerto = 6000;

        Socket Cliente = new Socket(host,Puerto);
        InetAddress i = Cliente.getInetAddress();
        System.out.println("Puerto Local: " + Cliente.getLocalPort());
        System.out.println("Puerto Remoto: " + Cliente.getPort());
        System.out.println("Nombre Host/IP: " + Cliente.getInetAddress());
        System.out.println("Host Remoto: " + i.getHostName().toString());
        System.out.println("IP Host Remoto: " + i.getHostAddress().toString());


    }
}
