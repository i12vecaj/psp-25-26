import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);

        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        salida.println("Hola servidor TCP");

        String respuesta = entrada.readLine();
        System.out.println("Servidor dice: " + respuesta);

        socket.close();
    }
}
