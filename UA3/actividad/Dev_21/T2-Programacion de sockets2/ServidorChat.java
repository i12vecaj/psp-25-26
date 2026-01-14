import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

    public static void main(String[] args) {
        int puertoServidor = 5000;

        try (ServerSocket serverSocket = new ServerSocket(puertoServidor)) {
            System.out.println("Servidor de chat iniciado en el puerto " + puertoServidor);

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("¡Nuevo cliente conectado!");
                System.out.println("Puerto local del servidor: " + cliente.getLocalPort());
                System.out.println("Puerto remoto del cliente: " + cliente.getPort());
                System.out.println("Dirección IP del cliente: " + cliente.getInetAddress());
                System.out.println("-----------------------------------");

                // Hilo para manejar la comunicación con el cliente
                new Thread(() -> manejarCliente(cliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void manejarCliente(Socket cliente) {
        try {
            BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salidaCliente = new PrintWriter(cliente.getOutputStream(), true);

            // Hilo para leer mensajes del cliente
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = entradaCliente.readLine()) != null) {
                        System.out.println("Cliente (" + cliente.getInetAddress() + "): " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Cliente desconectado: " + cliente.getInetAddress());
                }
            }).start();

            // Hilo para leer mensajes del teclado y enviarlos al cliente
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            new Thread(() -> {
                String msgServidor;
                try {
                    while ((msgServidor = teclado.readLine()) != null) {
                        salidaCliente.println(msgServidor);
                    }
                } catch (IOException e) {
                    System.out.println("Error enviando mensaje al cliente");
                }
            }).start();

        } catch (IOException e) {
            System.out.println("Error en la comunicación con el cliente: " + e.getMessage());
        }
    }
}
