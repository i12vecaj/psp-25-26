import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

    public static void main(String[] args) {
        int puertoServidor = 5000; // puerto del servidor

        try (ServerSocket serverSocket = new ServerSocket(puertoServidor)) {
            System.out.println("Servidor iniciado en el puerto " + puertoServidor);
            System.out.println("Esperando 2 clientes...");

            for (int i = 1; i <= 2; i++) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente " + i + " conectado!");
                System.out.println("Puerto local del servidor: " + cliente.getLocalPort());
                System.out.println("Puerto remoto del cliente: " + cliente.getPort());
                System.out.println("Dirección IP del cliente: " + cliente.getInetAddress());
                System.out.println("-----------------------------------");
            }

            System.out.println("Se han conectado 2 clientes. Servidor finalizando.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
