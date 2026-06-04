import java.io.IOException;

public class T2_Ejecutor {
    public static void main(String[] args) {
        /*
         * ProcessBuilder permite crear y ejecutar otro proceso desde Java.
         * En este caso, el proceso hijo ejecuta el programa T2_Lector.
         */
        ProcessBuilder procesoLector = new ProcessBuilder(
                "java",
                "-cp",
                System.getProperty("java.class.path"),
                "T2_Lector"
        );

        /*
         * inheritIO conecta la entrada, salida y error del proceso hijo con
         * los de este programa. Asi el usuario puede escribir el texto para
         * T2_Lector como si lo hubiese ejecutado directamente.
         */
        procesoLector.inheritIO();

        try {
            Process proceso = procesoLector.start();
            int codigoSalida = proceso.waitFor();

            if (codigoSalida != 0) {
                System.err.println("Error: T2_Lector termino con codigo de salida " + codigoSalida + ".");
                System.exit(codigoSalida);
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el proceso T2_Lector: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error: la espera del proceso T2_Lector fue interrumpida.");
            System.exit(1);
        }
    }
}
