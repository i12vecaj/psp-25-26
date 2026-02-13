/*
 * LecturaAsterisco.java
 *
 * Objetivo:
 *   - FR1: leer una cadena de caracteres desde la entrada estándar hasta recibir '*'
 *   - FR2: mostrar toda la información leída
 *
 * Control de errores:
 *   - Manejo de excepciones de entrada (InputMismatchException, IOException)
 *
 * Uso:
 *   java LecturaAsterisco
 *
 */

import java.util.Scanner;

public class LecturaAsterisco {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce caracteres. Para finalizar, escribe '*'.");

        try {
            while (true) {
                String input = sc.next(); // leer palabra por palabra
                if (input.contains("*")) { // si contiene '*', detener
                    int index = input.indexOf('*');
                    sb.append(input.substring(0, index)); // agregar hasta '*'
                    break;
                } else {
                    sb.append(input).append(" "); // agregar con espacio
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo la entrada: " + e.getMessage());
            System.exit(1);
        } finally {
            sc.close();
        }

        // FR2: mostrar toda la información leída
        System.out.println("Cadena leída: " + sb.toString());
    }
}
