/*
 * EjecutaAnalizador.java
 *
 * Objetivo:
 *   - FR3: ejecutar AnalizadorArgumentos desde Java
 *   - Mostrar en pantalla el significado del código de salida
 *
 * Control de errores:
 *   - Manejo de IOException e InterruptedException
 *
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EjecutaAnalizador {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Debe proporcionar al menos un argumento para pasar al programa.");
            return;
        }

        // Construir comando: java AnalizadorArgumentos arg1 arg2 ...
        List<String> comando = new ArrayList<>();
        comando.add("java");
        comando.add("AnalizadorArgumentos");
        for (String arg : args) {
            comando.add(arg);
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.inheritIO(); // opcional, para mostrar salida estándar del proceso si hubiera
            Process p = pb.start();

            int exitCode = p.waitFor();

            // Mostrar significado del código de salida
            switch (exitCode) {
                case 0:
                    System.out.println("Código 0: argumento es número entero >= 0");
                    break;
                case 1:
                    System.out.println("Código 1: número de argumentos < 1");
                    break;
                case 2:
                    System.out.println("Código 2: argumento es una cadena no numérica");
                    break;
                case 3:
                    System.out.println("Código 3: argumento es número entero < 0");
                    break;
                default:
                    System.out.println("Código desconocido: " + exitCode);
            }
        } catch (IOException e) {
            System.err.println("Error al ejecutar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Ejecución interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
