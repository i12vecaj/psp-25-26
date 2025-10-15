/*
 * Programa Principal.java
 * 
 * Funcionalidades:
 *  - Acepta argumentos desde main().
 *  - Devuelve un código de salida según las condiciones especificadas:
 *      1 → si no hay argumentos.
 *      2 → si el argumento no es un número entero.
 *      3 → si el argumento es un número entero negativo.
 *      0 → en cualquier otro caso.
 * 
 * Control de errores incluido.
 */
public class principal {
    public static void main(String[] args) {
        try {
            // FR1: Acepta argumentos desde main()
            if (args.length < 1) {
                System.exit(1); // No hay argumentos
            }

            int numero;

            try {
                // Intentar convertir el argumento a número entero
                numero = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // FR2: El argumento es una cadena no numérica
                System.exit(2);
                return; 
            }

            if (numero < 0) {
                System.exit(3); // FR2: Número entero menor que 0
            } else {
                System.exit(0); // FR2: Cualquier otro caso
            }

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            System.exit(-1); 
        }
    }
}
