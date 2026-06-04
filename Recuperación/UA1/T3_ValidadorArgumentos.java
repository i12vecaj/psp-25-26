public class T3_ValidadorArgumentos {
    public static void main(String[] args) {
        /*
         * Codigo 1: no se ha recibido ningun argumento en main().
         */
        if (args.length < 1) {
            System.exit(1);
        }

        /*
         * El enunciado habla de "el argumento", en singular, asi que se evalua
         * solo el primer argumento recibido.
         *
         * En Java todos los argumentos de main() llegan como String. Por eso,
         * interpretamos "si el argumento es una cadena" como "si no se puede
         * convertir a numero entero".
         */
        try {
            int numero = Integer.parseInt(args[0]);

            /*
             * Codigo 3: el argumento es un entero, pero su valor es menor que 0.
             */
            if (numero < 0) {
                System.exit(3);
            }

            /*
             * Codigo 0: hay argumento, es entero y no es negativo.
             */
            System.exit(0);
        } catch (NumberFormatException e) {
            /*
             * Codigo 2: el argumento no es un numero entero valido.
             */
            System.exit(2);
        }
    }
}
