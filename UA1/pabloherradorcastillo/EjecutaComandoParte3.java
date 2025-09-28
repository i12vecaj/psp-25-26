package pabloherradorcastillo;

import java.io.File;
import java.io.IOException;

public class EjecutaComandoParte3 {
    public static void main(String[] args) {
        // Comando de ping a google.com (Windows usa -n)
        ProcessBuilder pb = new ProcessBuilder(
                "ping", "-n", "4", "google.com");

        // Redirigir la salida estándar a un archivo
        pb.redirectOutput(new File("salida.txt"));

        try {
            // Iniciar el proceso
            Process proceso = pb.start();

            // Esperar a que termine
            int exitCode = proceso.waitFor();
            System.out.println("Proceso terminado con código: " + exitCode);
            System.out.println("La salida se ha guardado en el archivo salida.txt");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

