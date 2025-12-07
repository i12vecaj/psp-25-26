package Issue2.multihilo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (
            Socket socket = new Socket(host, port);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println(in.readLine());

            String texto;
            while (true) {
                System.out.print("> ");
                texto = teclado.readLine();
                out.println(texto);

                System.out.println(in.readLine());

                if ("salir".equalsIgnoreCase(texto)) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en cliente: " + e.getMessage());
        }
    }
}
