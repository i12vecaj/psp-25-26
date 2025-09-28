package pabloherradorcastillo;

import java.io.*;

public class EjecutaComandoParte4 {
    public static void main(String[] args) {
        try {
            // === PRIMER PROCESO: listar los archivos del directorio actual ===
            // En Windows: "cmd /c dir"
            ProcessBuilder listar = new ProcessBuilder("cmd", "/c", "dir");

            // Guardar la salida en un archivo listado.txt
            File archivoListado = new File("listado.txt");
            listar.redirectOutput(archivoListado);

            Process p1 = listar.start();
            int exitCode1 = p1.waitFor(); // Esperar a que termine

            System.out.println("Primer proceso terminado con código: " + exitCode1);

            // === SEGUNDO PROCESO: mostrar el contenido de listado.txt ===
            // Solo se ejecuta si el primero tuvo éxito (exit code 0)
            if (exitCode1 == 0) {
                ProcessBuilder mostrar = new ProcessBuilder("cmd", "/c", "type", "listado.txt");
                Process p2 = mostrar.start();

                // Mostrar el contenido en consola
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(p2.getInputStream()))) {

                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        System.out.println(linea);
                    }
                }

                int exitCode2 = p2.waitFor();
                System.out.println("\nSegundo proceso terminado con código: " + exitCode2);
            } else {
                System.out.println("El primer proceso falló. No se ejecuta el segundo.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
