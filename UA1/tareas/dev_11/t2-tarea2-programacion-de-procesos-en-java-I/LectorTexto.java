import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LectorTexto {

    public static void main(String[] args) {
        try {
            String texto = leerHastaElAsterisco();
            mostrarTextoDelUsuario(texto);
        } catch (IOException e) {
            System.out.println("Error al leer desde la entrada: " + e.getMessage());
        }
    }

    public static String leerHastaElAsterisco() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder texto = new StringBuilder();
        System.out.println("Introduce texto (finalizará con al llegar al *):");

        int caracter;
        while ((caracter = reader.read()) != -1) {
            if ((char) caracter == '*') {
                break;
            }
            texto.append((char) caracter);
        }

        return texto.toString(); // Devuelve todo el texto introducido antes del asterisco
    }

    public static void mostrarTextoDelUsuario(String texto) {
        System.out.println(" ");
        System.out.println("Texto introducido:");
        System.out.println(texto);
    }
}