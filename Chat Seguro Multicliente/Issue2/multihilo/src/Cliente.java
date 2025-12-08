import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try (
                // Me conecto al servidor en localhost
                Socket socket = new Socket("localhost", 5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Leo el mensaje de bienvenida del servidor
            System.out.println(in.readLine());
            String texto;

            // Mientras yo escriba cosas por consola se las mando al servidor
            while ((texto = teclado.readLine()) != null) {
                out.println(texto);
                System.out.println(in.readLine()); // Y muestro la respuesta del servidor
                if (texto.equalsIgnoreCase("salir")) break; // Si escribo salir, cierro
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}