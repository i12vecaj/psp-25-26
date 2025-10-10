public class Main {
    public static void main(String[] args) {
        /*
        Comprobamos si se le ha pasado algun argumento, si no se ha pasado ninguno
        devolveremos el codigo 1
         */
        if (args.length < 1) {
            System.exit(1);
        }
        /*Asignamos el argumento que hemos introducido a una variable para
        comprobar si es un entero o una cadena
         */
        String argumento = args[0];

        /*
         Comprobamos si es un numero y si es mayor que 0, si se cumple devolvera
         codigo 3, si no es un numero y es una cadena devolver el codigo 2.
         Si no es un entero o una cadena devolvera el codigo 0
         */
        try {
            int numero = Integer.parseInt(argumento);
            if (numero < 0) {
                System.exit(3);
            }

            System.exit(0);

        } catch (NumberFormatException e) {
            System.exit(2);
        }
    }
}

