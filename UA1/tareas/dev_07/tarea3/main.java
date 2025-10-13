public class main {

    public static void main(String[] args) {
        // Verificar si se han pasado argumentos
        if (args.length < 1) {
            System.exit(1); // Código de salida 1: No hay argumentos
        }

        try {
            // Intentar convertir el argumento en número entero
            int numero = Integer.parseInt(args[0]);

            // Verificar si el número es menor que 0
            if (numero < 0) {
                System.exit(3); // Código de salida 3: Número negativo
            } else {
                System.exit(0); // Código de salida 0: Número válido
            }

        } catch (NumberFormatException e) {
            // Si el argumento no es un número entero válido
            System.exit(2); // Código de salida 2: Argumento no numérico
        }
    }
}
