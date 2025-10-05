import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        StringBuilder input = new StringBuilder();
        System.out.println("Introduce letras (acaba con '*'):");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int letrita;
            while ((letrita = reader.read()) != -1) {
                char letrota = (char) letrita;
                if (letrota == '*') {
                    break;
                }
                input.append(letrota);
            }

            System.out.println("Lo que has escrito:");
            System.out.println(input.toString());

        } catch (IOException e) {
            System.err.println("Error : " + e.getMessage());
        }
    }
}
