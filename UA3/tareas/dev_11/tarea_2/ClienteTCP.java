import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int puerto = 6000;

        Socket cliente = new Socket(host, puerto);

        // Mostrar puertos y dirección
        System.out.println("Cliente conectado:");
        System.out.println("\tPuerto local: " + cliente.getLocalPort());
        System.out.println("\tPuerto remoto: " + cliente.getPort());
        System.out.println("\tIP remota: " + cliente.getInetAddress());

        // Flujos de comunicación
        DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
        DataInputStream entrada = new DataInputStream(cliente.getInputStream());

        // Enviar mensaje al servidor
        salida.writeUTF("Saludos al servidor desde el cliente");

        // Recibir mensaje del servidor
        System.out.println("Mensaje recibido del servidor: " + entrada.readUTF());

        // Cerrar flujos y socket
        entrada.close();
        salida.close();
        cliente.close();
    }
}
