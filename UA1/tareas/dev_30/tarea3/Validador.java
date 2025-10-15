public class Validador {
    public static void main(String[] args) {
        // en este if compruebo la cantidad de caracteres del argumento
        if (args.length<1){
            System.exit(1);
        }

        // creo una variable para guardar el argumento
        String argumento = args[0];

        // uso el control de errores de try catch
        try {// intento convertir la cadena a un entero, y si falla pasa al catch porque si es una cadena
            int numero = Integer.parseInt(argumento);

            if (numero < 0){// si alfinal es un entero, se comprueba si es negativo
                System.exit(3);
            }

            System.exit(0);// si no es negativo devilvemos 0

        }catch (NumberFormatException e){
            System.exit(2);// excepcion de error si la conversion falla y es una cadena
        }
    }
}
