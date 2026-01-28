import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {

        int Puerto = 6000;
        ServerSocket servidor = new ServerSocket(Puerto);
        System.out.println("Servidor iniciado. Esperando conexiones...");

        for (int i = 1; i <= 2; i++) {
            Socket cliente = servidor.accept();
            System.out.println("-----------------------------------");
            System.out.println("Cliente " + i + " conectado.");
            System.out.println("Puerto Local: " + cliente.getLocalPort());
            System.out.println("Puerto Remoto: " + cliente.getPort());
            System.out.println("Nombre IP: " + cliente.getInetAddress());
            System.out.println("Host Remoto: " + cliente.getInetAddress().getHostName());
            System.out.println("IP Host Remoto: " + cliente.getInetAddress().getHostAddress());
            cliente.close();
        }

        servidor.close();
    }

}