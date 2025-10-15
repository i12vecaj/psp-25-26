
public class ProcesoPrincipal {

    public static void main(String[] args) {
        try {
            // FR1: Admitir argumentos desde main()
            // FR2: Validar número de argumentos
            if (args.length < 1) {
                System.err.println("Error: Se requiere al menos un argumento");
                System.exit(1);
            }

            String argumento = args[0];

            // FR2: Validar si el argumento es una cadena (no numérico)
            if (!esNumeroEntero(argumento)) {
                System.err.println("El argumento '" + argumento + "' es una cadena de texto");
                System.exit(2);
            }

            // FR2: Convertir y validar si es número entero menor que 0
            int numero = Integer.parseInt(argumento);
            if (numero < 0) {
                System.err.println("El número " + numero + " es menor que 0");
                System.exit(3);
            }

            // FR2: Cualquier otro caso (número entero >= 0)
            System.out.println("Éxito: El argumento '" + argumento + "' es un número entero mayor o igual a 0");
            System.exit(0);

        } catch (Exception e) {
            // Control de errores: Captura cualquier excepción no esperada
            System.err.println("Error inesperado: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Método auxiliar para verificar si una cadena representa un número entero
     *
     * @param str Cadena a verificar
     * @return true si es número entero, false en caso contrario
     */
    private static boolean esNumeroEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}