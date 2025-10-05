
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.lang.Process;

public class EjecutorProceso {

    public static void main(String[] args) {

        System.out.println("--- Ejecutor de Proceso (Programa 2) ---");


        String classpath = "out/production/Tarea2";

        String[] comando = {"java", "-cp", classpath, "LectorCadena"};

        try {
            // 1. Creación del Proceso: Configura la ejecución del comando.
            ProcessBuilder pb = new ProcessBuilder(comando);

            // Redirige la entrada, salida y error estándar del proceso hijo al proceso padre.
            pb.inheritIO();

            System.out.println("Iniciando la ejecución del proceso hijo 'LectorCadena'...");

            Process proceso = pb.start();

            int exitCode = proceso.waitFor();

            System.out.println("\n--- Fin de Ejecución del Proceso ---");
            System.out.println("El proceso hijo 'LectorCadena' ha finalizado.");
            System.out.println("Código de Salida (Exit Code): " + exitCode);

            // Control de Errores:
            if (exitCode == 0) {
                System.out.println("Resultado: Ejecución completada con ÉXITO.");
            } else {
                System.err.println("Resultado: El proceso hijo ha finalizado con ERRORES. Código: " + exitCode);
                System.exit(1);
            }

        } catch (IOException e) {
            // Control de Errores: Captura si no se puede iniciar el comando (ej: archivo de clase no encontrado).
            System.err.println("ERROR: No se pudo iniciar el proceso. Verifique la disponibilidad del archivo de clase: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("ERROR: El hilo de espera del proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("ERROR: Error inesperado al gestionar el proceso: " + e.getMessage());
            System.exit(1);
        }
    }
}