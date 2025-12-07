package Issue2.multihilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            out.println("✅ Bienvenido al servidor multihilo");

            String mensaje;
            while ((mensaje = in.readLine()) != null) {

                if (mensaje.equalsIgnoreCase("salir")) {
                    out.println("👋 Hasta luego");
                    break;
                }

                out.println("Servidor dice: " + mensaje);
            }

        } catch (IOException e) {
            System.out.println("Error con cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Ignorar
            }
            System.out.println("Cliente desconectado.");
        }
    }
}
