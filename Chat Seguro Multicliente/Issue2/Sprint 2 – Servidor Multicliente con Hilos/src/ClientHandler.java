import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Bienvenido al servidor multihilo!");

            String mensaje;
            while ((mensaje = in.readLine()) != null) {

                if (mensaje.equalsIgnoreCase("adios")) {
                    out.println("Hasta luego!");
                    break;
                }

                out.println("Recibido: " + mensaje);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        }
    }
}
