public class Main {
    public static void main(String[] args) {

        //Si no hay argumentos sale con 1
        if (args.length < 1) {
            System.exit(1);
        }

        try {
            // Hacemos que el argumento sea un entero
            int numero = Integer.parseInt(args[0]);

            // Si es menor que 0 devuelve 3
            if (numero < 0) {
                System.exit(3);
            } else {
                // Cualquier otro caso devuelve 0
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            System.exit(2);
        }


    }
}