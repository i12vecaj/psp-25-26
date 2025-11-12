package PROCESOS;
// EjecutorLector.java
// Programa que ejecuta LectorCadena.java como un proceso hijo.
// FR3, Control de errores, Documentación
import java.io.IOException;

public class EjecutorLector {
    public static void main(String[] args) {
        try {
            // Ejecuta el programa LectorCadena
            ProcessBuilder pb = new ProcessBuilder("java", "LectorCadena");
            pb.inheritIO(); // Redirige la entrada/salida del proceso hijo a la consola actual
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();
            System.out.println("El proceso hijo terminó con código: " + exitCode);
        } catch (IOException e) {
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
