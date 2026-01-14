import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Tarea2_Servidor {

    public static void main(String[] args) {
        int puerto = 5000;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

            for (int i = 1; i <= 2; i++) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente " + i + " conectado:");

                System.out.println("  Puerto local del servidor: " + cliente.getLocalPort());
                System.out.println("  Puerto remoto del cliente: " + cliente.getPort());
                System.out.println("  Dirección IP del cliente: " + cliente.getInetAddress().getHostAddress());
                System.out.println("----------------------------------");
            }

            System.out.println("Servidor finaliza tras aceptar dos clientes.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

