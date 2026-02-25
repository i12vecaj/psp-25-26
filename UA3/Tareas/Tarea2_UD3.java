import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        int puerto = 6000;
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en puerto " + puerto + ". Esperando 2 clientes...");

            for (int i = 1; i <= 2; i++) {
                Socket cliente = servidor.accept(); // Bloquea hasta que llega un cliente
                System.out.println("\n--- Cliente " + i + " conectado ---");
                System.out.println("Puerto Local del Servidor: " + cliente.getLocalPort());
                System.out.println("Puerto Remoto (del Cliente): " + cliente.getPort());
                
                // Cerramos la conexión con este cliente pero mantenemos el servidor vivo para el siguiente
                cliente.close();
            }
            
            System.out.println("\nSe han recibido los 2 clientes. Cerrando servidor.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
