import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(6001)) {
            Socket cliente = servidor.accept();
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(
                    cliente.getOutputStream(), true);
            String mensaje = entrada.readLine();
            salida.println(mensaje.toUpperCase());
            cliente.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}