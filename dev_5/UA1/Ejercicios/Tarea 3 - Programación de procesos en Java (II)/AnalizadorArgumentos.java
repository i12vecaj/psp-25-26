/*
 * AnalizadorArgumentos.java
 *
 * Objetivo:
 *   - FR1: aceptar argumentos desde main()
 *   - FR2: devolver códigos de salida según la regla:
 *       1 -> menos de 1 argumento
 *       2 -> argumento es cadena no numérica
 *       3 -> argumento es número entero < 0
 *       0 -> cualquier otro caso (entero >= 0)
 *
 * Control de errores:
 *   - Manejo de excepción NumberFormatException al convertir argumentos
 *
 */

public class AnalizadorArgumentos {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(1); // menos de un argumento
        }

        String arg = args[0];

        try {
            int num = Integer.parseInt(arg);
            if (num < 0) {
                System.exit(3); // número entero menor que 0
            } else {
                System.exit(0); // número entero >= 0
            }
        } catch (NumberFormatException e) {
            // No es un número, asumimos que es cadena
            System.exit(2);
        }
    }
}
