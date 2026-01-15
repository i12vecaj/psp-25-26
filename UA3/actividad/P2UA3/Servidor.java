import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket servidorSocket = new ServerSocket(6000);

            Socket primerClienteSocket = servidorSocket.accept();
            System.out.println("Informacion del Primer Cliente:");
            System.out.print("Puerto Local del Servidor: ");
            System.out.println(primerClienteSocket.getLocalPort());
            System.out.print("Puerto Remoto del Cliente: ");
            System.out.println(primerClienteSocket.getPort());

            Socket segundoClienteSocket = servidorSocket.accept();
            System.out.println("Informacion del Segundo Cliente:");
            System.out.print("Puerto Local del Servidor: ");
            System.out.println(segundoClienteSocket.getLocalPort());
            System.out.print("Puerto Remoto del Cliente: ");
            System.out.println(segundoClienteSocket.getPort());

            primerClienteSocket.close();
            segundoClienteSocket.close();
            servidorSocket.close();
        } catch (IOException excepcionEntradaSalida) {
            System.out.println("Se ha producido un error en el servidor");
        }
    }
}