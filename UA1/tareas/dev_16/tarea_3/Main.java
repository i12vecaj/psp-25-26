import java.io.IOException;

/**
  Programa que ejecuta el validador de argumentos (Validador.java) y muestra mensajes
  según el código de retorno del proceso.

  Esto sería el Ejecutor.
 */

public class Main {

    public static void main(String[] args) {
        try {
            // Crear el comando para ejecutar el otro programa
            // Argumento para cambiar
            String argumento = "hola"; // Probrar con: "hola", "10", "-5", o sin argumento

            System.out.println("=== Iniciando prueba con argumento: " + argumento + " ===");

            // también se puede usar pb.directory
            String classpath = System.getProperty("java.class.path");

            // ejecución del validador
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, "Validador", argumento);
            pb.inheritIO(); // Para poder usar la terminal

            // Iniciar el proceso
            Process proceso = pb.start();

            // Esperar a que termine el proceso
            int codigoSalida = proceso.waitFor();

            // Mostrar mensaje según el código de retorno
            System.out.println("\n=== Resultado de la ejecución ===");
            interpretarCodigo(codigoSalida);

        } catch (IOException e) {
            // Error al crear o ejecutar el proceso
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
            e.printStackTrace();

        } catch (InterruptedException e) {
            // Error si el proceso es interrumpido
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            // Errores generales
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Dependiendo del código de salida que nos dé el Validador, tendremos un caso u otro.
    private static void interpretarCodigo(int codigo) {
        System.out.println("Código de salida: " + codigo);

        switch (codigo) {
            case 0:
                System.out.println("El argumento es un número válido");
                break;
            case 1:
                System.out.println("Error: No se proporcionaron argumentos");
                break;
            case 2:
                System.out.println("El argumento es un String");
                break;
            case 3:
                System.out.println("El argumento es un número negativo");
                break;
            default:
                System.out.println("Error desconocido con código: " + codigo);
                break;
        }
    }
}
