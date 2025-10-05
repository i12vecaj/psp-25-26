

import java.util.Scanner;
import java.util.NoSuchElementException;

public class LectorCadena {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // String para almacenar la cadena leída.
        String cadenaLeida = "";

        System.out.println("--- Lector de Cadena (Programa 1) ---");
        System.out.println("Introduce texto. La lectura finalizará cuando el texto introducido contenga un asterisco (*)");

        try {
            // Bucle que lee líneas completas (hasta el salto de línea) de la entrada.
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                // FR1: Comprueba el carácter de terminación.
                if (linea.contains("*")) {
                    // Si se encuentra el asterisco, se añade solo la parte anterior.
                    int index = linea.indexOf('*');
                    cadenaLeida = cadenaLeida + linea.substring(0, index);
                    break;
                }

                // FR1: Añade la línea completa leída y un salto de línea para mantener el formato original.
                cadenaLeida = cadenaLeida + linea + "\n";
            }

            // FR2: Muestra la información leída. Se usa trim() para limpiar el último salto de línea.
            String resultado = cadenaLeida.trim();
            System.out.println("\n--- Fin de Lectura ---");
            System.out.println("Cadena leída (sin el '*'):\n" + resultado);

        } catch (Exception e) {
            // Control de Errores: Captura cualquier otra excepción.
            System.err.println("ERROR: Error inesperado en el proceso de lectura: " + e.getMessage());
            System.exit(1);
        }
    }
}
