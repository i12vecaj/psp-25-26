package tarea2;

import java.io.*;

public class Ejecutor {
    public static void main(String[] args) {
        try {
            // Ejecutar el programa LectorCadena dentro del paquete tarea2
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "tarea2.LectorCadena");
            pb.redirectErrorStream(true);

            Process proceso = pb.start();

            // Leer la salida del proceso hijo
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int exitCode = proceso.waitFor();
            System.out.println("Proceso terminado con código: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


