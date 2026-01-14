import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000; // Puerto donde escucha el servidor
        int maxClientes = 2; // Número de clientes a aceptar

        try {
            // 1. Iniciamos el servidor en el puerto 6000
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado. Escuchando en el puerto: " + puerto);

            // 2. Bucle para aceptar 2 clientes
            for (int i = 1; i <= maxClientes; i++) {
                System.out.println("Esperando al cliente " + i + "...");
                
                // El método accept() bloquea la ejecución hasta que entra una conexión
                Socket cliente = servidor.accept(); 

                System.out.println("\n--- Cliente " + i + " conectado ---");
                
                // 3. Mostrar información de puertos
                // getLocalPort() devuelve el puerto del servidor (6000)
                // getPort() devuelve el puerto efímero desde donde se conecta el cliente
                System.out.println("Puerto Local (Servidor): " + cliente.getLocalPort());
                System.out.println("Puerto Remoto (Cliente): " + cliente.getPort());
                
                // Cerramos la conexión con este cliente específico
                cliente.close(); 
            }

            // Cerramos el servidor después de atender a los 2 clientes
            servidor.close();
            System.out.println("\nSe han atendido los 2 clientes. Servidor finalizado.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
