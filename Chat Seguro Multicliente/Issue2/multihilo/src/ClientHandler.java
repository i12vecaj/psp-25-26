import java.io.*;
import java.net.*;


public class ClientHandler implements Runnable {

    private Socket socket; // Aquí guardo el socket que representa al cliente

    public ClientHandler(Socket socket) {
        // Me guardo el socket del cliente para trabajar con él
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                // Leo lo que me mande el cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Y le escribo respuestas
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Bienvenido"); // Mensaje que envío siempre que un cliente entra
            String line;

            // Bucle donde me quedo escuchando al cliente mientras no cierre
            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("salir")) {
                    // Si me dice salir cierro
                    out.println("Hasta luego");
                    break;
                }
                // Devuelvo el mensaje para confirmar que lo he recibido
                out.println("Servidor: " + line);
            }

        } catch (IOException e) {
            System.out.println("Error con cliente");
        } finally {
            try {
                socket.close(); // Cierro el socket cuando termino
            } catch (IOException ignored) {
            }

        }
    }
}