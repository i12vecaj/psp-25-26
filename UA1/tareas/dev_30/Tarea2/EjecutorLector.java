import java.io.IOException;

public class EjecutorLector {

    public static void main(String[] args) {
        try {
            // Obtiene el classpath actual del programa en ejecución (lo que usa IntelliJ)
            String classPathActual = System.getProperty("java.class.path");

            // Imprime el classpath por si quieres verificarlo
            System.out.println("Classpath actual: " + classPathActual);

            // Crea el proceso con ese mismo classpath
            ProcessBuilder builder = new ProcessBuilder(
                    "java", "-cp", classPathActual, "LectorEntrada"
            );

            builder.inheritIO(); // Redirige entrada/salida a la consola de IntelliJ

            Process proceso = builder.start();
            int codigoSalida = proceso.waitFor();

            System.out.println("=== Proceso finalizado con código: " + codigoSalida + " ===");

        } catch (IOException | InterruptedException e) {
            System.err.println(" Error al ejecutar el proceso: " + e.getMessage());
        }
    }
}
