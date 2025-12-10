import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true)
        ) {
            out.println("Bienvenido al servidor multicliente");
            out.println("Escribe 'exit' para salir.");

            String message;
            while ((message = in.readLine()) != null) {

                if (message.equalsIgnoreCase("exit")) {
                    out.println("👋 Hasta luego");
                    break;
                }

                out.println("Servidor recibe: " + message);
            }

        } catch (IOException e) {
            System.err.println("Error con cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error cerrando socket");
            }
        }
    }
}
