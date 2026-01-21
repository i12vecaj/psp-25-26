import java.net.*;
import java.io.*;

public class ServidorTCP {

    private static final int PUERTO = 5000;
    private static final int MAX_CLIENTES = 2;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("===============================================================");
            System.out.println("SERVIDOR TCP INICIADO");
            System.out.println("===============================================================");
            System.out.println("Esperando conexiones en el puerto: " + PUERTO);
            System.out.println("Maximo de clientes: " + MAX_CLIENTES);
            System.out.println("===============================================================");
            System.out.println();

            for (int i = 1; i <= MAX_CLIENTES; i++) {
                System.out.println("Esperando cliente #" + i + "...");
                Socket clienteSocket = serverSocket.accept();

                System.out.println();
                System.out.println("---------------------------------------------------------------");
                System.out.println("CLIENTE #" + i + " CONECTADO");
                System.out.println("---------------------------------------------------------------");

                mostrarInformacionCliente(clienteSocket, i);

                System.out.println("---------------------------------------------------------------");
                System.out.println();
            }

            System.out.println("===============================================================");
            System.out.println("Se han conectado los " + MAX_CLIENTES + " clientes permitidos");
            System.out.println("===============================================================");

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                    System.out.println("Servidor cerrado correctamente");
                } catch (IOException e) {
                    System.err.println("Error al cerrar el servidor: " + e.getMessage());
                }
            }
        }
    }

    private static void mostrarInformacionCliente(Socket socket, int numeroCliente) {
        System.out.println("Puerto LOCAL del servidor:  " + socket.getLocalPort());
        System.out.println("Puerto REMOTO del cliente:  " + socket.getPort());
        System.out.println("IP del cliente:             " + socket.getInetAddress().getHostAddress());
        System.out.println("Nombre del host cliente:    " + socket.getInetAddress().getHostName());
    }
}