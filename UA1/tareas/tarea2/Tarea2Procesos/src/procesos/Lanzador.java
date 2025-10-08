package procesos;
import java.io.IOException;

/**
 * Ejecuta la clase Lector como proceso independiente usando ProcessBuilder.
 *
 * FR3: Ejecutar el programa anterior
 * Control de errores incluido
 */
public class Lanzador {
    public static void main(String[] args) {
        // Usa el mismo classpath que está usando IntelliJ en este proceso
        String classpath = System.getProperty("java.class.path");

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", classpath, "procesos.Lector"
            );
            pb.inheritIO();              // Hereda entrada/salida de la consola
            Process proceso = pb.start();// Inicia el proceso hijo
            int exit = proceso.waitFor();// Espera a que termine
            System.out.println("Proceso hijo finalizado con código: " + exit);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al lanzar el proceso: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
