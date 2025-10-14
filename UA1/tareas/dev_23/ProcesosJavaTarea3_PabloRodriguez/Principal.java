public class Principal {

    public static void main(String[] args) {

        // 1. COMPROBACIÓN DE ARGUMENTOS (para código de salida 1)
        // Sirve para verificar si se proporcionó al menos un argumento.
        if (args.length < 1) {
            System.exit(1);
        }

        String argumento = args[0];

        try {
            // Intenta convertir el primer argumento a un número entero.
            int numero = Integer.parseInt(argumento);

            // 2. COMPROBACIÓN DE NEGATIVO (para código de salida 3)
            // Sirve para verificar si el número es menor que cero.
            if (numero < 0) {
                System.exit(3);
            }

            // 3. CASO CORRECTO (para código de salida 0)
            // Sirve si el argumento es un número entero igual o mayor que cero.
            else {
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            // 4. COMPROBACIÓN DE CADENA (para código de salida 2)
            // Sirve para capturar el error si el argumento no se puede convertir a número (es texto).
            System.exit(2);
        }
    }
}