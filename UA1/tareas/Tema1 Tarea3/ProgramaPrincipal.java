public class ProgramaPrincipal {

    public static void main(String[] args) {
        try {
            // Verificar si el número de argumentos es menor que 1
            if (args.length < 1) {
                System.out.println("Error: No se proporcionaron argumentos");
                System.exit(1);
            }

            // Obtener el primer argumento
            String argumento = args[0];
            System.out.println("Argumento recibido: " + argumento);

            // Intentar convertir el argumento a número entero
            try {
                int numero = Integer.parseInt(argumento);

                // Verificar si el número es menor que 0
                if (numero < 0) {
                    System.out.println("El argumento es un numero negativo: " + numero);
                    System.exit(3);
                } else {
                    // Número válido (>= 0)
                    System.out.println("El argumento es un numero valido: " + numero);
                    System.exit(0);
                }

            } catch (NumberFormatException e) {
                // FR2.2: El argumento no es un número, es una cadena
                System.out.println("El argumento es una cadena de texto: " + argumento);
                System.exit(2);
            }

        } catch (Exception e) {
            // Control de errores generales
            System.err.println("Error inesperado en el programa: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}