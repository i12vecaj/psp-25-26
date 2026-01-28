import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {
        // Datos del servidor al que vamos a llamar
        final String HOST = "localhost";
        final int PUERTO = 6000;

        try {
            System.out.println("Intentando conectar al servidor...");

            // 1. Establecemos la conexión (El Handshake)
            Socket socket = new Socket(HOST, PUERTO);

            // 2. Obtenemos la dirección IP
            InetAddress direccionRemota = socket.getInetAddress();

            System.out.println("\n--- CONEXIÓN ESTABLECIDA ---");


            // getLocalPort(): El sistema operativo asignó un puerto libre a este cliente (ej: 50123).
            // getPort(): El puerto al que nos conectamos en el servidor (6000).
            System.out.println(" -> Mi Puerto Local (Cliente): " + socket.getLocalPort());
            System.out.println(" -> Puerto Remoto (Servidor): " + socket.getPort());
            System.out.println(" -> IP de la máquina remota:  " + direccionRemota.getHostAddress());

            // 3. Cerramos
            socket.close();
            System.out.println("\nCliente finalizado.");

        } catch (UnknownHostException e) {
            System.out.println("No se encuentra el host");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida o servidor no disponible");
        }
    }
}