public class ValidadorArgumentos {
    public static void main(String[] args) {
        int exitCode = validarArgumentos(args);
        System.exit(exitCode);
    }

    public static int validarArgumentos(String[] args) {
        if(args.length < 1) {
            return 1; // No tiene ningún argumento
        }

        try {
            int num = Integer.parseInt(args[0]);
            if(num < 0){
                return 3; // El argumento es un entero menor que 0
            }
        } catch (NumberFormatException e) {
            return 2; // El argumento no es un entero, sino una cadena
        }
        return 0; // Argumento válido
    }
}

