package Tarea3;

/**
 * Clase: Programa1
 * Descripción: Implementa FR1 y FR2. Procesa el primer argumento y devuelve 
 * un código de salida específico usando System.exit().
 *
 * Códigos de Salida (FR2):
 * 1: Si args.length < 1 (No hay argumentos).
 * 2: Si el argumento no es un número entero (es una cadena).
 * 3: Si el argumento es un número entero menor que 0.
 * 0: En cualquier otro caso (número entero >= 0).
 */
public class Programa1 {
    public static void main(String[] args) {
        // FR2: Si el número de argumentos es < 1 debe devolver 1
        if (args.length < 1) {
            System.err.println("ERROR (1): Se requiere al menos un argumento.");
            System.exit(1); // Devuelve 1
        }

        String argumento = args[0];

        try {
            // Intenta convertir el primer argumento a un número entero.
            int numero = Integer.parseInt(argumento);

            // FR2: Si el argumento es un número entero menor que 0 debe devolver 3
            if (numero < 0) {
                System.out.println("Salida: Argumento válido y negativo: " + numero);
                System.exit(3); // Devuelve 3
            } else {
                // FR2: En cualquier otro caso debe devolver 0 (número entero >= 0)
                System.out.println("Salida: Argumento válido y no negativo: " + numero);
                System.exit(0); // Devuelve 0
            }

        } catch (NumberFormatException e) {
            // Control de errores: Captura si no es un número entero válido
            // FR2: Si el argumento es una cadena debe devolver 2
            System.err.println("ERROR (2): El argumento \"" + argumento + "\" no es un número entero válido (cadena).");
            System.exit(2); // Devuelve 2
        }
    }
}
