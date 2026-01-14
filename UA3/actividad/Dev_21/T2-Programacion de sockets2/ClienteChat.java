import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteChat {

    public static void main(String[] args) {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Introduce la IP del servidor: ");
            String servidor = teclado.readLine().trim();
            int puertoServidor = 5000;

            Socket socket = new Socket(servidor, puertoServidor);
            System.out.println("Conectado al servidor " + servidor);

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Hilo para leer mensajes del servidor
            new Thread(() -> {
                String msgServidor;
                try {
                    while ((msgServidor = entrada.readLine()) != null) {
                        System.out.println("Servidor: " + msgServidor);
                    }
                } catch (IOException e) {
                    System.out.println("Servidor desconectado");
                }
            }).start();

            // Hilo principal para enviar mensajes al servidor
            String msgCliente;
            while ((msgCliente = teclado.readLine()) != null) {
                salida.println(msgCliente);
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor: " + e.getMessage());
        }
    }
}
