/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Fecha: 21/10/2025
 */

import java.io.IOException;

public class EjecutorPrograma {

    public static void main(String[] args) {

        try {
            // Obtenemos el classpath actual
            String classpath = System.getProperty("java.class.path");

            // Creamos el ProcessBuilder para ejecutar ProgramaPrincipal
            ProcessBuilder pb;

            if (args.length > 0) {
                // Si hay argumentos, los pasamos
                pb = new ProcessBuilder("java", "-cp", classpath, "ProgramaPrincipal", args[0]);
            } else {
                // Si no hay argumentos, ejecutamos sin ellos
                pb = new ProcessBuilder("java", "-cp", classpath, "ProgramaPrincipal");
            }

            // Iniciamos el proceso
            Process proceso = pb.start();

            // Esperamos a que termine y obtenemos el código de salida
            int codigoSalida = proceso.waitFor();

            // Mostramos el mensaje correspondiente según el código
            switch (codigoSalida) {
                case 0:
                    System.out.println("Correcto: El argumento es un número válido >= 0 (código 0)");
                    break;
                case 1:
                    System.out.println("Error: No se ha introducido ningún argumento (código 1)");
                    break;
                case 2:
                    System.out.println("Error: El argumento no es un número, es una cadena (código 2)");
                    break;
                case 3:
                    System.out.println("Error: El número es negativo (código 3)");
                    break;
                default:
                    System.out.println("Error desconocido: código " + codigoSalida);
                    break;
            }

        } catch (IOException e) {
            // Error al intentar ejecutar el programa
            System.err.println("Error al ejecutar ProgramaPrincipal: " + e.getMessage());

        } catch (InterruptedException e) {
            // Error si el proceso se interrumpe
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}