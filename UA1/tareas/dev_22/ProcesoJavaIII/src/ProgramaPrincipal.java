/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Fecha: 21/10/2025
 */

public class ProgramaPrincipal {

    // Método principal que valida los argumentos.
    public static void main(String[] args) {

        try {
            // Comprobamos si se ha pasado algún argumento
            if (args.length < 1) {
                System.exit(1); // Código 1: no hay argumentos
            }

            // Intentamos convertir el argumento a número entero
            int numero = Integer.parseInt(args[0]);

            // Comprobamos si el número es negativo
            if (numero < 0) {
                System.exit(3); // Código 3: número negativo
            } else {
                System.exit(0); // Código 0: todo correcto
            }

        } catch (NumberFormatException e) {
            // Si no se puede convertir a número, es una cadena
            System.exit(2); // Código 2: no es un número
        }
    }
}