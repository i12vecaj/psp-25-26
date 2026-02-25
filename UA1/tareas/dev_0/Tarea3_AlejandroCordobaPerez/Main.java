/**
 * Clase Principal.
 * Este programa analiza sus argumentos de línea de comandos y devuelve un
 * código de salida
 * específico utilizando System.exit() según el siguiente criterio:
 * - 1: Si no se proporciona ningún argumento.
 * - 2: Si el único argumento es una cadena (no un número entero válido).
 * - 3: Si el único argumento es un número entero menor que 0.
 * - 0: En cualquier otro caso (argumento entero >= 0).
 */
public class Main {

    /**
     * Punto de entrada del programa.
     * 
     * @param args Array de argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        // FR2 - Condición 1: Si el número de argumentos es < 1 (devuelve 1)
        if (args.length < 1) {
            System.err.println("ERROR: No se proporcionaron argumentos.");
            System.exit(1);
        }

        // FR1: Admite argumentos desde main()
        // Solo consideramos el primer argumento para el resto de las comprobaciones.
        String argumento = args[0];

        try {
            // Intenta convertir el argumento a un número entero
            int numero = Integer.parseInt(argumento);

            // FR2 - Condición 3: Si el argumento es un número entero menor que 0 (devuelve
            // 3)
            if (numero < 0) {
                System.out.println("Argumento: " + numero + ". Es un número entero negativo.");
                System.exit(3);
            }
            // FR2 - Condición 4: En cualquier otro caso (devuelve 0)
            else {
                System.out.println("Argumento: " + numero + ". Es un número entero mayor o igual a 0.");
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            // Implementación de control de errores.
            // FR2 - Condición 2: Si el argumento es una cadena (devuelve 2)
            // Se lanza NumberFormatException si el argumento no es un entero válido.
            System.out.println("Argumento: '" + argumento + "'. Es una cadena o un número no entero.");
            System.exit(2);
        }
    }
}