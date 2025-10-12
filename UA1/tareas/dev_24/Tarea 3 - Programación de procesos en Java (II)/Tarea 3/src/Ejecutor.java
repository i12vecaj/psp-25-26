/**Autor: Antonio Rodríguez Cortés*/


import java.io.*;

public class Ejecutor {
    public static void main(String[] args) {
        try {
            // Control de errores: verificar que hay argumentos
            if (args.length < 1) {
                System.out.println("Uso: java Ejecutor <argumento>");
                System.out.println("Ejemplo: java Ejecutor 5");
                return;
            }

            // FR3: Construir y ejecutar el proceso Comprobador
            ProcessBuilder pb = new ProcessBuilder("java", "Comprobador", args[0]);
            Process proceso = pb.start();

            // Esperar a que termine y obtener código de salida
            int codigo = proceso.waitFor();

            // Mostrar mensaje según el código de salida
            switch (codigo) {
                case 0 -> System.out.println("✓ Todo correcto: número entero válido (" + args[0] + ")");
                case 1 -> System.out.println("✗ Error: No se pasó ningún argumento al programa Comprobador");
                case 2 -> System.out.println("✗ Error: El argumento '" + args[0] + "' no es un número entero");
                case 3 -> System.out.println("✗ Error: El número " + args[0] + " es menor que 0");
                default -> System.out.println("✗ Código de salida desconocido: " + codigo);
            }

        } catch (IOException e) {
            // Control de errores: problema al crear o ejecutar el proceso
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
            System.err.println("Asegúrate de que Comprobador.class está compilado y en el mismo directorio");

        } catch (InterruptedException e) {
            // Control de errores: proceso interrumpido
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();

        } catch (Exception e) {
            // Control de errores: cualquier otro error inesperado
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}