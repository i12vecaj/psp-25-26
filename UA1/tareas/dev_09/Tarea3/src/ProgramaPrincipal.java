public class ProgramaPrincipal {

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Error: No se proporcionaron argumentos");
                System.exit(1);
            }

            String valor = args[0];
            System.out.println("Argumento recibido: " + valor);

            try {
                int numero = Integer.parseInt(valor);

                if (numero < 0) {
                    System.out.println("El argumento es un número negativo: " + numero);
                    System.exit(3);
                } else {
                    System.out.println("El argumento es un número válido: " + numero);
                    System.exit(0);
                }

            } catch (NumberFormatException e) {
                System.out.println("El argumento es una cadena de texto: " + valor);
                System.exit(2);
            }

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
