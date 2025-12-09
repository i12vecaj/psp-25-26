import java.io.*;
import java.net.*;

public class ClienteTerminal {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6000);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Servidor: " + in.readLine());

            String texto;
            while ((texto = teclado.readLine()) != null) {
                out.println(texto);
                System.out.println("Servidor responde: " + in.readLine());
                if (texto.equalsIgnoreCase("adios")) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
