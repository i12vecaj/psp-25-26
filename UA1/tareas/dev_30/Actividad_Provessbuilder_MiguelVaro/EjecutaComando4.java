package Parte4;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class EjecutaComando4 {
    public static void main(String[] args) {
        String OS = System.getProperty("os.name").toLowerCase();
        File archivoSalida = new File("listado_directorio_temp.txt");
        try {
            System.out.println("--- Ejecutando Proceso 1: Listar archivos del directorio ---");

            List<String> comando1 = new ArrayList<>();
            if (OS.contains("win")) {
                comando1.add("cmd");
                comando1.add("/c");
                comando1.add("dir");
            } else {
                comando1.add("ls");
                comando1.add("-l");
            }

            ProcessBuilder pb1 = new ProcessBuilder(comando1);
            pb1.redirectOutput(archivoSalida);
            pb1.redirectErrorStream(true);

            Process proceso1 = pb1.start();
            int exitCode1 = proceso1.waitFor();

            System.out.println("Proceso 1 finalizado con código de salida: " + exitCode1);

            if (exitCode1 == 0) {
                System.out.println("\n--- Proceso 1 exitoso. Ejecutando Proceso 2: Mostrar contenido del archivo ---");

                List<String> comando2 = new ArrayList<>();
                if (OS.contains("win")) {
                    comando2.add("cmd");
                    comando2.add("/c");
                    comando2.add("type");
                    comando2.add(archivoSalida.getName());
                } else {
                    comando2.add("cat");
                    comando2.add(archivoSalida.getName());
                }

                ProcessBuilder pb2 = new ProcessBuilder(comando2);
                pb2.redirectErrorStream(true);

                Process proceso2 = pb2.start();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso2.getInputStream()))) {
                    String linea;
                    System.out.println("\n--- Contenido del Archivo de Salida ---");
                    while ((linea = reader.readLine()) != null) {
                        System.out.println(linea);
                    }
                }

                int exitCode2 = proceso2.waitFor();
                System.out.println("\nProceso 2 finalizado con código de salida: " + exitCode2);

            } else {
                System.err.println("Proceso 1 falló (código != 0). El Proceso 2 no se ejecutará.");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Ocurrió un error al ejecutar los procesos: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            if (archivoSalida.exists()) {
                if (archivoSalida.delete()) {
                    System.out.println("\nArchivo temporal '" + archivoSalida.getName() + "' eliminado.");
                } else {
                    System.err.println("No se pudo eliminar el archivo temporal: " + archivoSalida.getName());
                }
            }
        }
    }
}