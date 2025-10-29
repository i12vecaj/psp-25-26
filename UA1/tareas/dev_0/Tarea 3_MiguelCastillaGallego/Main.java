public class Main {

    public static void main(String[] args) {

        // Verifica si se han pasado argumentos por línea de comandos
        if (args.length < 1) {
            // Si no hay argumentos, muestra un mensaje de error por la salida de error estándar
            System.err.println("ERROR: No se proporcionaron argumentos.");
            // Finaliza el programa con código de salida 1 (indica error por falta de argumentos)
            System.exit(1);
        }

        // Guarda el primer argumento recibido
        String argumento = args[0];

        try {
            // Intenta convertir el argumento a un número entero
            int numero = Integer.parseInt(argumento);

            // Si el número es negativo
            if (numero < 0) {
                // Muestra el valor y especifica que es un número entero negativo
                System.out.println("Argumento: " + numero + ". Es un número entero negativo.");
                // Finaliza el programa con código de salida 3
                System.exit(3);
            }
            else {
                // Si el número es positivo o cero
                System.out.println("Argumento: " + numero + ". Es un número entero mayor o igual a 0.");
                // Finaliza el programa con código de salida 0 (indica ejecución correcta)
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            // Si ocurre una excepción al intentar convertir el argumento (no es un número entero)
            System.out.println("Argumento: '" + argumento + "'. Es una cadena o un número no entero.");
            // Finaliza el programa con código de salida 2
            System.exit(2);
        }
    }
}
