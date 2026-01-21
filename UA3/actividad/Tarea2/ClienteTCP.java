import java.net.*;
import java.io.*;

public class ClienteTCP {

    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        Socket socket = null;

        try {
            System.out.println("===============================================================");
            System.out.println("CLIENTE TCP");
            System.out.println("===============================================================");
            System.out.println("Intentando conectar al servidor...");
            System.out.println("Host: " + HOST);
            System.out.println("Puerto: " + PUERTO);
            System.out.println();

            socket = new Socket(HOST, PUERTO);

            System.out.println("---------------------------------------------------------------");
            System.out.println("CONEXION ESTABLECIDA");
            System.out.println("---------------------------------------------------------------");

            mostrarInformacionSocket(socket);

            System.out.println("---------------------------------------------------------------");
            System.out.println();

            System.out.println("Presiona ENTER para cerrar la conexion...");
            System.in.read();

        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de conexion: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                    System.out.println("Conexion cerrada correctamente");
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket: " + e.getMessage());
                }
            }
        }
    }

    private static void mostrarInformacionSocket(Socket socket) {
        System.out.println("Puerto LOCAL del cliente:   " + socket.getLocalPort());
        System.out.println("Puerto REMOTO del servidor: " + socket.getPort());
        System.out.println("IP LOCAL del cliente:       " + socket.getLocalAddress().getHostAddress());
        System.out.println("IP REMOTA del servidor:     " + socket.getInetAddress().getHostAddress());
        System.out.println("Nombre del host remoto:     " + socket.getInetAddress().getHostName());
    }
}