import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EjecutorProcesos {


    public static void main(String[] args) {
        // FR3: Ejecutar el programa anterior
        ProcessBuilder processBuilder = new ProcessBuilder();

        try {
            // Configurar el comando para ejecutar ProcesoPrincipal
            // Nota: Asegúrate de que ProcesoPrincipal.class esté en el classpath
            processBuilder.command("java", "ProcesoPrincipal");

            if (args.length > 0) {
                // Si se proporcionan argumentos, pasarlos al proceso principal
                String[] comandoCompleto = new String[2 + args.length];
                comandoCompleto[0] = "java";
                comandoCompleto[1] = "ProcesoPrincipal";
                System.arraycopy(args, 0, comandoCompleto, 2, args.length);
                processBuilder.command(comandoCompleto);
            }

            System.out.println("Ejecutando ProcesoPrincipal...");

            // Iniciar el proceso
            Process proceso = processBuilder.start();

            // Capturar la salida estándar del proceso
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );

            // Capturar la salida de error del proceso
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(proceso.getErrorStream())
            );

            // Mostrar salida estándar
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Salida: " + linea);
            }

            // Mostrar salida de error
            while ((linea = errorReader.readLine()) != null) {
                System.err.println("Error: " + linea);
            }

            // Esperar a que el proceso termine y obtener el código de salida
            int codigoSalida = proceso.waitFor();

            // Mostrar el resultado según el código de salida
            System.out.println("\n=== RESULTADO DE LA EJECUCIÓN ===");
            System.out.println("Código de salida: " + codigoSalida);

            switch (codigoSalida) {
                case 0:
                    System.out.println("Situación: ÉXITO - El argumento es un número entero mayor o igual a 0");
                    break;
                case 1:
                    System.out.println("Situación: ERROR - Número de argumentos insuficiente");
                    break;
                case 2:
                    System.out.println("Situación: El argumento es una cadena de texto");
                    break;
                case 3:
                    System.out.println("Situación: El argumento es un número entero menor que 0");
                    break;
                default:
                    System.out.println("Situación: Código de salida desconocido");
                    break;
            }

        } catch (IOException e) {
            // Control de errores: Problemas de E/S al ejecutar el proceso
            System.err.println("Error de E/S al ejecutar el proceso: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Control de errores: El proceso fue interrumpido
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            // Control de errores: Captura cualquier otra excepción
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}