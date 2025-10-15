// ValidadorArgumentos.java
// Autor: Bernardo Cubero
// FR1: admite argumentos desde main()
// FR2: devuelve códigos con System.exit()

public class ValidadorArgumentos {

    public static void main(String[] args) {
        int exitCode = validarArgumentos(args);
        System.exit(exitCode);
    }

    public static int validarArgumentos(String[] args) {
        // Caso 1: sin argumentos
        if (args.length < 1) {
            return 1;
        }

        try {
            int num = Integer.parseInt(args[0]);
            // Caso 3: entero negativo
            if (num < 0) {
                return 3;
            }
            // Caso OK
            return 0;
        } catch (NumberFormatException e) {
            // Caso 2: no es entero (cadena)
            return 2;
        }
    }
}
