// Runner.java
// Nombre: Miguel Castilla Gallego
// Fecha: 05/10/2025
// Versión 1.0

import java.io.*;

public class Runner {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".", "Reader.java");
            pb.inheritIO(); // Muestra la entrada/salida en la consola
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("El programa Reader terminó con código: " + exitCode);
            } else {
                System.out.println("Reader finalizó correctamente");
            }
        } catch (IOException e) {
            System.err.println("Error al ejecutar Reader: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}
