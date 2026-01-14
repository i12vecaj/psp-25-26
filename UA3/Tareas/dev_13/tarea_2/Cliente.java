import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; // O la IP "127.0.0.1"
        int puerto = 6000;

        try {
            System.out.println("Intentando conectar al servidor...");
            
            // 1. Conectar al servidor
            Socket cliente = new Socket(host, puerto);
            
            // 2. Obtener información de la conexión
            System.out.println("\n--- Conexión establecida ---");
            
            // Puerto Local: El puerto aleatorio que el S.O. asignó a este cliente
            System.out.println("Puerto Local (Cliente): " + cliente.getLocalPort());
            
            // Puerto Remoto: El puerto del servidor al que nos conectamos (6000)
            System.out.println("Puerto Remoto (Servidor): " + cliente.getPort());
            
            // Dirección IP de la máquina remota (Servidor)
            InetAddress direccionRemota = cliente.getInetAddress();
            System.out.println("IP de máquina remota (Servidor): " + direccionRemota.getHostAddress());

            // 3. Cerrar conexión
            cliente.close();
            System.out.println("\nCliente desconectado.");

        } catch (IOException e) {
            System.out.println("Error: No se pudo conectar con el servidor. ¿Está encendido?");
        }
    }
}
