package ProcesosJava;

import java.io.IOException;

// Crea un proceso que ejecute el programa LectorCadena.

public class EjecutaLector {
    public static void main(String[] args) {
        try {
            // Crear el proceso usando ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder("java", "procesosjava.LectorCadena");

            // Hacer que el proceso use la consola actual
            pb.inheritIO();

            // Iniciar el proceso
            Process proceso = pb.start();

            // Esperar a que termine
            int codigoSalida = proceso.waitFor();
            System.out.println("\nEl proceso ha terminado con código: " + codigoSalida);

        } catch (IOException e) {
            System.err.println("❌ Error al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("❌ Proceso interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
