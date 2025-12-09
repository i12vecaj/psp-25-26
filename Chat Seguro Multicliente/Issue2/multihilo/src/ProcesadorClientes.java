import java.io.*;
import java.net.Socket;

public class ProcesadorClientes implements Runnable {

    private Socket socket;

    public ProcesadorClientes(Socket socketCliente) {
        this.socket = socketCliente;
    }

    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        ) {

            salida.println("Bienvenido al servidor multicliente!");

            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Cliente dice: " + mensaje);

                if (mensaje.equalsIgnoreCase("bye")) {
                    salida.println("Hasta luego.");
                    break;
                }

                salida.println("Eco: " + mensaje);
            }

        } catch (IOException e) {
            System.out.println("Error manejando cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}

            System.out.println("Cliente desconectado.");
        }
    }
}
