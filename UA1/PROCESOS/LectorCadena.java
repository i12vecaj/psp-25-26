package PROCESOS;
// LectorCadena.java
// Programa que lee una cadena desde la entrada estándar hasta recibir un asterisco '*'
// y luego muestra la información leída.
// FR1, FR2, Control de errores, Documentación
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LectorCadena {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        System.out.println("Introduce caracteres (finaliza con '*'):");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int c;
            while ((c = br.read()) != -1) {
                if ((char) c == '*') {
                    break;
                }
                sb.append((char) c);
            }
        } catch (IOException e) {
            System.err.println("Error al leer la entrada: " + e.getMessage());
            return;
        }
        System.out.println("Cadena leída:");
        System.out.println(sb.toString());
    }
}
