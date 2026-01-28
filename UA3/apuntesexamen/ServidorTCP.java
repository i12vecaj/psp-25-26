import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(5000);
        System.out.println("Servidor TCP esperando...");

        Socket cliente = servidor.accept();
        System.out.println("Cliente conectado");

        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(cliente.getInputStream())
        );
        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

        String mensaje = entrada.readLine();
        System.out.println("Cliente dice: " + mensaje);

        salida.println("Mensaje recibido 👍");

        cliente.close();
        servidor.close();
    }
}
