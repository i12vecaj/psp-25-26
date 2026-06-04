import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class T3_Ejecutor {
    private static final String CLASE_PRINCIPAL = "T3_ValidadorArgumentos";

    public static void main(String[] args) {
        CasoPrueba[] casos = {
                new CasoPrueba("Sin argumentos", new String[] {}),
                new CasoPrueba("Argumento de texto", new String[] {"hola"}),
                new CasoPrueba("Entero negativo", new String[] {"-5"}),
                new CasoPrueba("Entero no negativo", new String[] {"10"})
        };

        for (CasoPrueba caso : casos) {
            ejecutarCaso(caso);
        }
    }

    private static void ejecutarCaso(CasoPrueba caso) {
        List<String> comando = new ArrayList<>();
        comando.add("java");
        comando.add("-cp");
        comando.add(System.getProperty("java.class.path"));
        comando.add(CLASE_PRINCIPAL);

        for (String argumento : caso.argumentos()) {
            comando.add(argumento);
        }

        /*
         * ProcessBuilder crea un proceso independiente que ejecuta el programa
         * principal. Despues waitFor() permite recoger el codigo devuelto por
         * System.exit().
         */
        ProcessBuilder procesoValidador = new ProcessBuilder(comando);
        procesoValidador.inheritIO();

        try {
            Process proceso = procesoValidador.start();
            int codigoSalida = proceso.waitFor();

            System.out.println("Caso: " + caso.descripcion());
            System.out.println("Codigo devuelto: " + codigoSalida);
            System.out.println("Explicacion: " + explicarCodigo(codigoSalida));
            System.out.println();
        } catch (IOException e) {
            System.err.println("Error al iniciar " + CLASE_PRINCIPAL + ": " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error: la espera del proceso fue interrumpida.");
            System.exit(1);
        }
    }

    private static String explicarCodigo(int codigoSalida) {
        return switch (codigoSalida) {
            case 0 -> "El argumento existe, es un numero entero y no es negativo.";
            case 1 -> "No se ha recibido ningun argumento.";
            case 2 -> "El primer argumento recibido es una cadena no numerica.";
            case 3 -> "El primer argumento es un numero entero menor que 0.";
            default -> "Codigo no esperado devuelto por el programa principal.";
        };
    }

    private record CasoPrueba(String descripcion, String[] argumentos) {
    }
}
