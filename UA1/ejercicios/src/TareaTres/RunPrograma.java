package TareaTres;

import java.io.IOException;
import java.util.Arrays;

public class RunPrograma {

    public static void main(String[] args) {

        try {
            // Obtenemos el classpath actual de Java
            String classpath = System.getProperty("java.class.path");

            // Construimos el comando para ejecutar MainProgram con el classpath correcto
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", classpath, "TareaTres.MainProgram"
            );

            // Agregamos todos los argumentos que se pasen a RunPrograma
            if (args.length > 0) {
                pb.command().addAll(Arrays.asList(args));
            }

            // Permite que el proceso hijo use la misma entrada/salida
            pb.inheritIO();

            // Ejecuta el proceso
            Process process = pb.start();
            int exitCode = process.waitFor(); // Espera a que termine

            // Interpretar el código de salida de MainProgram
            switch (exitCode) {
                case 0 -> System.out.println("Programa ejecutado correctamente.");
                case 1 -> System.out.println("Error: no se proporcionaron argumentos.");
                case 2 -> System.out.println("Error: el argumento es una cadena.");
                case 3 -> System.out.println("Error: el número es menor que 0.");
                default -> System.out.println("Error inesperado, código de salida: " + exitCode);
            }

        } catch (IOException e) {
            System.err.println("Error al ejecutar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Ejecución interrumpida: " + e.getMessage());
        }

    }
}
