/**
 * ProgramaPrincipal
 *
 * Códigos de salida (System.exit):
 * 1 -> no se pasó ningún argumento
 * 2 -> el primer argumento no es un entero válido
 * 3 -> el primer argumento es un entero negativo
 * 0 -> en cualquier otro caso (entero >= 0)
 *
 * Nota: solo se evalúa el primer argumento.
 */
public class ProgramaPrincipal {

    public static void main(String[] args) {
        int exitCode;

        try {
            if (args == null || args.length < 1) {
                exitCode = 1;
            } else {
                String arg = args[0] == null ? "" : args[0].trim();

                if (arg.isEmpty()) {
                    exitCode = 2;
                } else {
                    try {
                        int value = Integer.parseInt(arg);
                        exitCode = (value < 0) ? 3 : 0;
                    } catch (NumberFormatException nfe) {
                        exitCode = 2;
                    }
                }
            }
        } catch (Exception e) {
            // Control de errores no previsto
            System.err.println("Error: " + e.getMessage());
            exitCode = 99; // código no contemplado
        }

        System.exit(exitCode);
    }
}
