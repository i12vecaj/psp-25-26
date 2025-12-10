import java.io.*;
import java.net.*;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        try (Socket s = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println(in.readLine()); // mensaje de bienvenida
            String line;
            while ((line = console.readLine()) != null) {
                out.println(line);
                System.out.println("Servidor: " + in.readLine());
                if (line.equalsIgnoreCase("bye")) break;
            }
        }
    }
}
