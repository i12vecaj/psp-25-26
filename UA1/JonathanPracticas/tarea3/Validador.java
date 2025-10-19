/**
  Códigos de retorno:
   1: Si no hay argumentos
   2: Si el argumento es una cadena (no numérica)
   3: Si el argumento es un número menor que 0
   0: En cualquier otro caso (número válido >= 0)
 */


public class Validador {

    public static void main(String[] args) {
        try {
            // Verificar si hay al menos un argumento
            if (args.length < 1) {
                System.out.println("Error: No se proporcionaron argumentos");
                System.exit(1); // Retorna 1 si no hay argumentos
            }

            // Obtener el primer argumento
            String argumento = args[0];
            System.out.println("Argumento recibido: " + argumento);

            // Convertir el argumento a número entero
            try {
                int numero = Integer.parseInt(argumento);

                // Verificar si el número es menor que 0
                if (numero < 0) {
                    System.out.println("El número es negativo: " + numero);
                    System.exit(3); // Retorna 3 si el número es menor que 0
                } else {
                    System.out.println("El número es válido: " + numero);
                    System.exit(0); // Retorna 0 si el número es válido
                }

            } catch (NumberFormatException e) {
                // Si no se puede convertir a número, es una cadena
                System.out.println("El argumento es una cadena de texto");
                System.exit(2); // Retorna 2 si es una cadena
            }

        } catch (Exception e) {
            // Control de errores generales
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1); // Código de error genérico
        }
    }
}
