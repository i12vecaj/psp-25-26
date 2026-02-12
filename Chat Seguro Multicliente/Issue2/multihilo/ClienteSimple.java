import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSimple {
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                Scanner teclado = new Scanner(System.in)) {
            System.out.println("Conectado al servidor.");
            System.out.println("Servidor: " + entrada.readLine());

            String mensaje = "";

            while (!mensaje.equalsIgnoreCase("adios")) {
                System.out.print("Tú: ");
                mensaje = teclado.nextLine();

                salida.println(mensaje);

                String respuesta = entrada.readLine();
                System.out.println("Servidor: " + respuesta);
            }

        } catch (IOException e) {
            System.out.println("Error en cliente: " + e.getMessage());
        }
    }
}
