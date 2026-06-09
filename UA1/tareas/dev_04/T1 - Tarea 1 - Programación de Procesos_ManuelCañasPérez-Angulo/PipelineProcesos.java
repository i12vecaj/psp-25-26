package Parte4;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PipelineProcesos {
    public static void main(String[] args) {
        String identificadorSO = System.getProperty("os.name").toLowerCase();
        File documentoTemporal = new File("listado_directorio_temp.txt");
        
        try {
            System.out.println("--- Ejecutando Proceso 1: Listar archivos del directorio ---");

            List<String> argsComando1 = new ArrayList<>();
            if (identificadorSO.contains("win")) {
                argsComando1.add("cmd");
                argsComando1.add("/c");
                argsComando1.add("dir");
            } else {
                argsComando1.add("ls");
                argsComando1.add("-l");
            }

            ProcessBuilder compiladorP1 = new ProcessBuilder(argsComando1);
            compiladorP1.redirectOutput(documentoTemporal);
            compiladorP1.redirectErrorStream(true);

            Process p1 = compiladorP1.start();
            int retornoP1 = p1.waitFor();

            System.out.println("Proceso 1 finalizado con código de salida: " + retornoP1);

            // Condición para encadenar secuencialmente la lectura si el primer comando tuvo éxito
            if (retornoP1 != 0) {
                System.err.println("Proceso 1 falló (código != 0). El Proceso 2 no se ejecutará.");
            } else {
                System.out.println("\n--- Proceso 1 exitoso. Ejecutando Proceso 2: Mostrar contenido del archivo ---");

                List<String> argsComando2 = new ArrayList<>();
                if (identificadorSO.contains("win")) {
                    argsComando2.add("cmd");
                    argsComando2.add("/c");
                    argsComando2.add("type");
                    argsComando2.add(documentoTemporal.getName());
                } else {
                    argsComando2.add("cat");
                    argsComando2.add(documentoTemporal.getName());
                }

                ProcessBuilder compiladorP2 = new ProcessBuilder(argsComando2);
                compiladorP2.redirectErrorStream(true);

                Process p2 = compiladorP2.start();
                try (BufferedReader lectorBuf = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
                    String contenidoLinea;
                    System.out.println("\n--- Contenido del Archivo de Salida ---");
                    while (true) {
                        contenidoLinea = lectorBuf.readLine();
                        if (contenidoLinea == null) {
                            break;
                        }
                        System.out.println(contenidoLinea);
                    }
                }

                int retornoP2 = p2.waitFor();
                System.out.println("\nProceso 2 finalizado con código de salida: " + retornoP2);
            }

        } catch (IOException | InterruptedException ex) {
            System.err.println("Ocurrió un error al ejecutar los procesos: " + ex.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            // Sección encargada de limpiar los archivos auxiliares generados en disco duro
            if (documentoTemporal.exists()) {
                if (!documentoTemporal.delete()) {
                    System.err.println("No se pudo eliminar el archivo temporal: " + documentoTemporal.getName());
                } else {
                    System.out.println("\nArchivo temporal '" + documentoTemporal.getName() + "' eliminado.");
                }
            }
        }
    }
}