import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;

public class Cliente {

    public static void main(String[] args) {
        try {
            Socket socketCliente = new Socket("localhost", 6000);
            InetAddress direccionRemota = socketCliente.getInetAddress();

            System.out.println("Datos de la conexion actual:");
            System.out.print("Puerto Local del Cliente: ");
            System.out.println(socketCliente.getLocalPort());
            System.out.print("Puerto Remoto del Servidor: ");
            System.out.println(socketCliente.getPort());
            System.out.print("Direccion IP Remota: ");
            System.out.println(direccionRemota.getHostAddress());

            socketCliente.close();
        } catch (IOException excepcionConexion) {
            System.out.println("No se ha podido conectar con el servidor");
        }
    }
}