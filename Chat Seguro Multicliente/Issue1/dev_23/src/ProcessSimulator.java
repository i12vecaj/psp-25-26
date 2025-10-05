import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.lang.Process;
import java.util.ArrayList;
import java.util.List;

public class ProcessSimulator {

    // Define el archivo de log (logs/resultados_multiproceso.txt)
    private static final File LOG_FILE = new File("logs", "resultados_multiproceso.txt");

    // Configuración del Classpath para que funcione en IntelliJ
    private static final String CLASSPATH = System.getProperty("java.class.path");

    // Definición de las clases de tarea
    private static final String[] TASK_CLASSES = {
            "Script1",
            "Script2",
            "Script3"
    };

    public static void main(String[] args) {

        // 1. Asegurar la existencia del directorio de logs
        LOG_FILE.getParentFile().mkdirs();

        // 2. Ejecución secuencial y medición de tiempo
        long sequentialTime = executeSequentially(TASK_CLASSES);

        // 3. Ejecución paralela y medición de tiempo
        long parallelTime = executeConcurrently(TASK_CLASSES);

        // 4. Guardar resultados
        String logContent = String.format(
                "--- Resultados de Ejecución Multi-Proceso ---\n" +
                        "Tareas Ejecutadas:\n" +
                        " - Script1: Carga Alta (5B iteraciones)\n" +
                        " - Script2: Carga Media (3B iteraciones)\n" +
                        " - Script3: Carga Media-Alta (4B iteraciones)\n" +
                        "Tiempo total de ejecución SECUENCIAL: %.2f segundos)\n" +
                        "Tiempo total de ejecución PARALELA: %.2f segundos\n",
                (double)sequentialTime / 1000.0,
                (double)parallelTime / 1000.0
        );

        logResults(logContent);

        System.out.println("\n--- SIMULACIÓN FINALIZADA ---");
        System.out.println(logContent);
        System.out.println("Resultados guardados en: " + LOG_FILE.getAbsolutePath());
    }

    //Ejecuta las tareas de forma secuencial.

    private static long executeSequentially(String[] tasks) {
        System.out.println("\n>>> MODO: SECUENCIAL");
        long startTime = System.currentTimeMillis();

        for (String taskClass : tasks) {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", CLASSPATH, taskClass);
                pb.inheritIO();
                Process p = pb.start();

                // Esperar a que el proceso termine.
                p.waitFor();

            } catch (IOException | InterruptedException e) {
                System.err.println("Error en ejecución secuencial de " + taskClass + ". Revise el CLASSPATH: " + e.getMessage());
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    /**
     * Ejecuta las tareas de forma concurrente.
     */
    private static long executeConcurrently(String[] tasks) {
        System.out.println("\n>>> MODO: PARALELO (Todas a la vez)");
        long startTime = System.currentTimeMillis();
        List<Process> processes = new ArrayList<>();

        // 1. Iniciar todos los procesos sin esperar
        for (String taskClass : tasks) {
            try {
                // Comando: java -cp <CLASSPATH> <Clase>
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", CLASSPATH, taskClass);
                pb.inheritIO();

                Process p = pb.start();
                processes.add(p);

            } catch (IOException e) {
                System.err.println("Error al iniciar proceso " + taskClass + ": " + e.getMessage());
            }
        }

        // 2. Esperar a que todos los procesos terminen (la espera total será el tiempo del script más largo)
        for (Process p : processes) {
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return System.currentTimeMillis() - startTime;
    }


     // Guarda el resultado en el archivo de log.

    private static void logResults(String content) {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("ERROR al escribir el archivo de log: " + e.getMessage());
        }
    }
}