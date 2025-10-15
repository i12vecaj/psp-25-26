public class ValidadorArgumentos {

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Error: No se proporcionaron argumentos");
                System.exit(1);
            }

            String argumento = args[0];

            try {
                int numero = Integer.parseInt(argumento);

                if (numero < 0) {
                    System.out.println("El argumento es un número negativo: " + numero);
                    System.exit(3);
                }

                System.out.println("El argumento es un número válido: " + numero);
                System.exit(0);

            } catch (NumberFormatException e) {
                System.out.println("El argumento es una cadena: " + argumento);
                System.exit(2);
            }

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            System.exit(1);
        }
    }
}