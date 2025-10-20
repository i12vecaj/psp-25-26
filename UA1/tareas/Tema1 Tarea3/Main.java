import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        // Casos de prueba que se van a ejecutar
        String[] casosPrueba = {
                "",
                "Hola",
                "-5",
                "10",
                "0"
        };

        System.out.println("=== EJECUTOR DE PROGRAMAPRINCIPAL ===\n");

        // Ejecutar cada caso de prueba
        for (int i = 0; i < casosPrueba.length; i++) {
            System.out.println("--- Caso de prueba " + (i + 1) + " ---");
            if (casosPrueba[i].isEmpty()) {
                ejecutarPrograma(null); // Ejecutar sin argumentos
            } else {
                ejecutarPrograma(casosPrueba[i]); // Ejecutar con argumento
            }
            System.out.println();
        }
    }

    // Ejecuta el ProgramaPrincipal con o sin argumento
    private static void ejecutarPrograma(String argumento) {
        ProcessBuilder pb;

        try {
            // Obtener el classpath actual del programa que está corriendo en IntelliJ
            String classPath = System.getProperty("java.class.path");

            if (argumento == null) {
                System.out.println("Ejecutando sin argumentos...");
                pb = new ProcessBuilder("java", "-cp", classPath, "ProgramaPrincipal");
            } else {
                System.out.println("Ejecutando con argumento: \"" + argumento + "\"");
                pb = new ProcessBuilder("java", "-cp", classPath, "ProgramaPrincipal", argumento);
            }

            pb.redirectErrorStream(true);

            Process proceso = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );

            String linea;
            System.out.println("Salida del programa:");
            while ((linea = reader.readLine()) != null) {
                System.out.println("  " + linea);
            }

            int codigoSalida = proceso.waitFor();
            interpretarCodigoSalida(codigoSalida);

        } catch (IOException e) {
            System.err.println("Error de E/S al ejecutar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    // Muestra el significado del código de salida
    private static void interpretarCodigoSalida(int codigo) {
        System.out.println("\n► Código de salida: " + codigo);
        System.out.print("► Interpretación: ");

        switch (codigo) {
            case 0:
                System.out.println("✓ El argumento es un número válido (>= 0)");
                break;
            case 1:
                System.out.println("✗ No se proporcionaron argumentos suficientes");
                break;
            case 2:
                System.out.println("✗ El argumento es una cadena de texto (no numérico)");
                break;
            case 3:
                System.out.println("✗ El argumento es un número negativo");
                break;
            default:
                System.out.println("⚠ Código de salida inesperado");
                break;
        }
    }
}
