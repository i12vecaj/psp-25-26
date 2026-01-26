import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        try(ServerSocket servidor = new ServerSocket(6666)) {
            System.out.println("Servidor: puerto 6666");

            for(int i=1; i<=2; i++) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente " + i + " conectado desde " 
                + cliente.getLocalPort() + " a " + cliente.getPort());
                cliente.close();
            }
} catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
