package Tarea3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Clase: Programa2
 * Descripción: Implementa FR3. Ejecuta 'Programa1' usando ProcessBuilder, 
 * gestiona su salida y muestra por pantalla el significado de su código de salida.
 *
 * Implementa el Control de Errores (manejo de IOException, InterruptedException).
 * Implementa la Documentación del código.
 */
public class Programa2 {
    public static void main(String[] args) {
        // Control de errores: Verificar que se pase un argumento para Programa1
        if (args.length < 1) {
            System.out.println("Uso: java Programa2 <argumento_para_Programa1>");
            System.out.println("Ejemplo: java Programa2 -5");
            return;
        }

        String argumentoAProcesar = args[0];

        // Configuración para ejecutar 'java Programa1 <argumento>'
        // ¡Asegúrese de que Programa1.class esté en el mismo directorio!
        ProcessBuilder pb = new ProcessBuilder("java", "Programa1", argumentoAProcesar);

        try {
            System.out.println(">>> Iniciando Programa1 con argumento: " + argumentoAProcesar + " <<<");

            // Inicia la ejecución del proceso hijo
            Process proceso = pb.start();

            // --- Captura de Salidas del Proceso Hijo ---
            
            // Capturar y mostrar la salida estándar (System.out) de Programa1
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = stdOut.readLine()) != null) {
                System.out.println("[Programa1 OUT]: " + linea);
            }

            // Capturar y mostrar la salida de error (System.err) de Programa1
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            while ((linea = stdErr.readLine()) != null) {
                System.out.println("[Programa1 ERR]: " + linea);
            }

            // Espera a que el proceso termine y obtiene su código de salida
            int codigoSalida = proceso.waitFor();

            // --- Interpretación del Código de Salida ---
            System.out.println("\n>>> Programa1 ha finalizado. <<<");
            System.out.println("CÓDIGO DE SALIDA DEVUELTO: " + codigoSalida);
            
            // Muestra por pantalla lo que sucede según el valor devuelto
            System.out.print("INTERPRETACIÓN: ");
            switch (codigoSalida) {
                case 0:
                    System.out.println("El argumento es un número entero mayor o igual que 0. (ÉXITO)");
                    break;
                case 1:
                    System.out.println("No se pasó ningún argumento al Programa1.");
                    break;
                case 2:
                    System.out.println("El argumento es una cadena (no pudo convertirse a entero).");
                    break;
                case 3:
                    System.out.println("El argumento es un número entero negativo (< 0).");
                    break;
                default:
                    System.out.println("Código de salida desconocido o inesperado.");
                    break;
            }

        } catch (IOException e) {
            // Control de errores: Error al iniciar el proceso (ej. 'java' no encontrado o clase no compilada)
            System.err.println("\n--- ERROR DE EJECUCIÓN ---");
            System.err.println("Error al intentar iniciar Programa1. ¿Está compilado y en el PATH? Detalle: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Control de errores: La espera por el proceso fue interrumpida
            System.err.println("El proceso fue interrumpido mientras esperaba su terminación.");
            Thread.currentThread().interrupt(); // Restaura el estado de interrupción
        }
    }
}
