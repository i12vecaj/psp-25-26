import java.io.*;
import java.net.*;

public class MultiThreadedServer {

    public static void main(String[] args) {
        int puerto = 6000;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en puerto " + puerto);

            while (true) {
                Socket socketCliente = servidor.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress());

                new Thread(new ClientHandler(socketCliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
