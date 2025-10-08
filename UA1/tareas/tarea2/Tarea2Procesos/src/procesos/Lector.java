package procesos;
import java.util.Scanner;

/**
 * Lee desde la entrada estándar hasta encontrar '*'
 * y luego muestra.
 *
 * FR1: Leer hasta '*'
 * FR2: Mostrar
 * Control de errores incluido
 */
public class Lector {
    public static void main(String[] args) {
        StringBuilder texto = new StringBuilder();

        System.out.println("Introduce texto (finaliza con *):");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String linea = sc.nextLine();
                if (linea.contains("*")) {
                    texto.append(linea, 0, linea.indexOf('*'));
                    break;
                }
                texto.append(linea).append(System.lineSeparator());
            }
        } catch (Exception e) {
            System.err.println("Error de lectura: " + e.getMessage());
        }

        System.out.println("\n--- Contenido introducido ---");
        System.out.println(texto.toString());
    }
}
