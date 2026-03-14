/*
 * EjecutaLectura.java
 *
 * Objetivo:
 *   - FR3: ejecutar el programa LecturaAsterisco desde Java
 *
 * Uso:
 *   java EjecutaLectura
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class EjecutaLectura {
    public static void main(String[] args) {
        try {
            // FR3: ejecutar programa LecturaAsterisco
            ProcessBuilder pb = new ProcessBuilder("java", "LecturaAsterisco");
            pb.inheritIO(); // heredamos la entrada/salida estándar
            Process p = pb.start();

            // esperar a que el proceso termine
            int exitCode = p.waitFor();
            System.out.println("Programa LecturaAsterisco finalizado con código: " + exitCode);
        } catch (IOException e) {
            System.err.println("Error al ejecutar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Ejecución interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
