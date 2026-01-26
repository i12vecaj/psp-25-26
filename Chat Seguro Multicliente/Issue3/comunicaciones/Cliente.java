package comunicaciones;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 7000)) {

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            Thread receptor = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        System.out.println(mensaje);
                    }
                } catch (IOException e) {
                    System.out.println("Conexión cerrada" + e.getMessage());
                }
            });

            receptor.start();

            while (true) {
                String texto = scanner.nextLine();
                salida.println(texto);

                if (texto.equalsIgnoreCase("/exit")) {
                    break;
                }
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
