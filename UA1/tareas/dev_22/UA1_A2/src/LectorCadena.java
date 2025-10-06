import java.util.Scanner;

/**
 * Programa: LectorCadena
 * Autor: Jose Antonio Roda Donoso
 */
public class LectorCadena {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder texto = new StringBuilder();

        System.out.println("Escribe texto (* para terminar):");

        try {
            // Bucle que lee caracteres hasta que se introduce '*'
            while (true) {
                char c = sc.next().charAt(0);
                if (c == '*') break;
                texto.append(c);
            }

            // Muestra por pantalla todo lo que se ha escrito
            System.out.println("Texto introducido: " + texto.toString());

        } catch (Exception e) {
            // Control de errores: por si ocurre algo al leer datos
            System.out.println("Error al leer los datos: " + e.getMessage());
        } finally {
            sc.close(); // Cierra el scanner para liberar recursos
        }
    }
}
