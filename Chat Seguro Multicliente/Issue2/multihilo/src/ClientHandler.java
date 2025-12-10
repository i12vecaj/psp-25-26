import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Bienvenido al servidor multicliente!");

            String linea;
            while ((linea = in.readLine()) != null) {
                System.out.println("De " + socket.getRemoteSocketAddress() + ": " + linea);
                if (linea.equalsIgnoreCase("bye") || linea.equalsIgnoreCase("adios")) {
                    out.println("Hasta luego!");
                    break;
                }
                out.println("Echo: " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error con cliente " + socket.getRemoteSocketAddress());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Cliente desconectado: " + socket.getRemoteSocketAddress());
        }
    }
}
