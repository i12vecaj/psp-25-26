import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 6001)) {
            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            System.out.println("Introduce un mensaje: ");
            String mensaje = scanner.nextLine();
            salida.println(mensaje);
            String respuesta = entrada.readLine();
            System.out.println("Respuesta: " + respuesta);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        scanner.close();
    }
}