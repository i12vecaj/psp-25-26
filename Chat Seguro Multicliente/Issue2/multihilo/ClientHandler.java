import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Bienvenido! Por favor, introduce tu nombre:");

            clientName = in.readLine();
            if (clientName == null) return;

            System.out.println("Cliente identificado como: " + clientName);
            out.println("Hola, " + clientName + "! Estás conectado. Escribe 'salir' para desconectarte.");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("salir".equalsIgnoreCase(inputLine)) {
                    break;
                }
                System.out.println(">> [" + clientName + "]: " + inputLine);
                out.println("Servidor echo: " + inputLine);
            }

            out.println("Hasta luego, " + clientName + "! Conexión cerrada.");
            System.out.println("Cliente " + clientName + " se ha desconectado.");

        } catch (IOException e) {
            if (clientName != null) {
                System.out.println("Cliente " + clientName + " (socket) ha perdido la conexión.");
            } else {
                System.out.println("Un cliente desconocido ha perdido la conexión.");
            }
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}