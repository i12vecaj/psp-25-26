import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        // Puerto donde el servidor "escuchará"
        final int PUERTO = 6000;

        try {
            // 1. Iniciamos el servidor
            ServerSocket servidor = new ServerSocket(PUERTO);
            System.out.println("--- Servidor iniciado esperando a 2 clientes ---");

            // 2. Bucle para aceptar exactamente 2 clientes
            for (int i = 1; i <= 2; i++) {
                // El programa se detiene aquí (bloqueo) hasta que entra un cliente
                Socket clienteConectado = servidor.accept();

                System.out.println("\nCLIENTE " + i + " CONECTADO:");
                System.out.println("------------------------------------------------");

                // getLocalPort(): Es el puerto del servidor (6000).
                // getPort(): Es el puerto desde donde viene el cliente (aleatorio/efímero).
                System.out.println(" -> Puerto Local (Mío/Servidor): " + clienteConectado.getLocalPort());
                System.out.println(" -> Puerto Remoto (Del Cliente): " + clienteConectado.getPort());

                // Cerramos la conexión con este cliente específico
                clienteConectado.close();
            }

            System.out.println("\nSe han atendido los 2 clientes. Cerrando servidor.");
            servidor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}