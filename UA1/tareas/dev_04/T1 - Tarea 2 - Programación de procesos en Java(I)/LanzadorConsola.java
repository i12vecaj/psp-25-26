import java.io.IOException;

public class LanzadorConsola {

    public static void main(String[] args) {
        try {
            // Rescatamos la ruta del entorno de ejecución de la máquina virtual de Java
            String pathClases = System.getProperty("java.class.path");

            // Volcado informativo del entorno mapeado
            System.out.println("Classpath actual: " + pathClases);

            // Estructuramos la ejecución del nuevo subproceso adjuntando el destino
            ProcessBuilder configProceso = new ProcessBuilder(
                    "java", "-cp", pathClases, "CapturadorTexto"
            );

            // Vinculamos los flujos de entrada y salida estándar con la consola activa
            configProceso.inheritIO();

            Process runtimeProceso = configProceso.start();
            int statusFinal = runtimeProceso.waitFor();

            System.out.println("=== Proceso finalizado con código: " + statusFinal + " ===");

        } catch (IOException | InterruptedException errorProceso) {
            System.err.println(" Error al ejecutar el proceso: " + errorProceso.getMessage());
        }
    }
}