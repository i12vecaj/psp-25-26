import java.io.IOException;

public class Ejecutor {
    public static void main(String[] args) {
        try {
            // Ruta al directorio de clases compiladas por IntelliJ
            String classpath = "C:\\Users\\Alex\\Desktop\\DAM\\2DAM\\PSPRO\\Tarea2\\out\\production\\Tarea2";

            // Crear el ProcessBuilder para ejecutar Main
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-cp",
                    classpath,
                    "Main" // nombre de la clase sin .java
            );

            pb.inheritIO(); // Conecta entrada/salida con la consola
            Process proceso = pb.start();

            int exitCode = proceso.waitFor(); // Espera a que Main termine

            if (exitCode != 0) {
                System.err.println("El programa Main terminó con código: " + exitCode);
            } else {
                System.out.println("Main finalizó correctamente");
            }

        } catch (IOException e) {
            System.err.println("Error al ejecutar Main: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}
