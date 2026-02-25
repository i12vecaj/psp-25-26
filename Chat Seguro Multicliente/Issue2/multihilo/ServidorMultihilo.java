import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMultihilo {

    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        System.out.println("Servidor multicliente iniciado en el puerto " + PUERTO + "...");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress());

                ProcesadorDeClientes manejador = new ProcesadorDeClientes(socketCliente);
                Thread hiloCliente = new Thread(manejador);

                hiloCliente.start();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}