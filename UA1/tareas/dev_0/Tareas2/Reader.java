// Reader.java
// Nombre: Miguel Castilla Gallego
// Fecha: 05/10/2025
// Versión 1.0

import java.util.Scanner;

public class Reader {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder buffer = new StringBuilder();

        System.out.println("Introduce texto (termina el texto con '*'):");

        try {
            while (true) {
                if (!sc.hasNext()) {
                    System.err.println("Error: fin de entrada antes de encontrar '*'");
                    sc.close();
                    System.exit(2);
                }

                String input = sc.next(); // El texto se lee palabra a palabra
                for (char c : input.toCharArray()) {
                    if (c == '*') {
                        // Asterisco encontrado
                        System.out.println("\n--- Contenido leído (sin '*') ---");
                        System.out.println(buffer.toString());
                        System.out.println("--- Fin del contenido ---");
                        sc.close();
                        return;
                    } else {
                        buffer.append(c);
                    }
                }
                buffer.append(" "); // Agregamos espacio entre palabras
            }
        } catch (Exception e) {
            System.err.println("Error de lectura: " + e.getMessage());
            sc.close();
            System.exit(1);
        }
    }
}
