import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        int puerto = 6000;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado en el puerto " + puerto);

        for (int i = 1; i <= 2; i++) {
            System.out.println("Esperando al cliente " + i + "...");
            Socket cliente = servidor.accept();

            // Mostrar puertos y direcciones
            System.out.println("Cliente " + i + " conectado:");
            System.out.println("\tPuerto local (servidor): " + cliente.getLocalPort());
            System.out.println("\tPuerto remoto (cliente): " + cliente.getPort());

            // Flujos de comunicación
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

            // Recibir mensaje del cliente
            String mensaje = entrada.readUTF();
            System.out.println("\tMensaje recibido: " + mensaje);

            // Enviar mensaje al cliente
            salida.writeUTF("Hola Cliente " + i + ", mensaje recibido correctamente");

            // Cerrar flujos y socket del cliente
            entrada.close();
            salida.close();
            cliente.close();
        }

        servidor.close();
        System.out.println("Servidor cerrado.");
    }
}
