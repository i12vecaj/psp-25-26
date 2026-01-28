import java.io.*;
import java.net.*;
import java.util.Scanner;

public class JarlClienteTCP {

    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 5000;

        try {
            Socket socket = new Socket(servidor, puerto);

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(), true
            );

            Scanner scanner = new Scanner(System.in);
            System.out.print("Escribe tu juramento al Valhalla: ");
            String mensaje = scanner.nextLine();

            salida.println(mensaje);

            String respuesta = entrada.readLine();
            System.out.println("Respuesta de Odín: " + respuesta);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}