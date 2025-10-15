/**
 *
 * Programa que devuelve distintos códigos de salida según el argumento recibido.
 *
 * Códigos de salida:
 * 1 -> No se ha introducido ningún argumento
 * 2 -> El argumento no es un número entero (es una cadena)
 * 3 -> El número es menor que 0
 * 0 -> En cualquier otro caso (entero >= 0)
 *
 */

public class Main {
    public static void main(String[] args) {
        // Si no hay argumentos -> código 1 (por lo tanto siempre que se ejecute esta clase sin ejecutar el lanzador
        // devolverá uno porque en este código no se introducen argumentos
        if (args.length < 1) {
            System.exit(1);
        }

        try {
            // Intentar convertir el argumento a número entero
            int numero = Integer.parseInt(args[0]);

            // Si el número es menor que 0 -> código 3
            if (numero < 0) {
                System.exit(3);
            }

            // Si el número es válido y >= 0 -> código 0
            System.exit(0);

        } catch (NumberFormatException e) {
            // Si no se puede convertir a número -> código 2
            System.exit(2);
        }
    }
}
