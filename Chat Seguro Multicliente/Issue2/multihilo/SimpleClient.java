import java.io.*;
import java.net.Socket;

public class SimpleClient {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 5000);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in))
        ) {
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            String input;
            while ((input = keyboard.readLine()) != null) {
                out.println(input);
                System.out.println(in.readLine());

                if (input.equalsIgnoreCase("exit")) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
