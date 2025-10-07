import java.util.Scanner;

public class LectorEntrada {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder textoLeido = new StringBuilder();
        System.out.println("Introduce texto. Escribe '*' para finalizar:");

        try {
            while (true) {
                String linea = scanner.nextLine();

                // Si la línea contiene '*', se detiene la lectura
                if (linea.contains("*")) {
                    int index = linea.indexOf('*');
                    textoLeido.append(linea, 0, index);
                    break;
                }

                textoLeido.append(linea).append(System.lineSeparator());
            }

            System.out.println("=== Contenido introducido ===");
            System.out.println(textoLeido.toString());

        } catch (Exception e) {
            System.err.println(" Error durante la lectura de datos: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
