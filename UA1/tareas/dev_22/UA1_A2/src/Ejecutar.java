import java.io.IOException;

/**
 * Programa: Ejecutar
 * Autor: Jose Antonio Roda Donoso
 */
public class Ejecutar {
    public static void main(String[] args) {
        try {
            // Se define la ruta donde está la clase compilada de LectorCadena
            String ruta = "C:\\Users\\Jose\\IdeaProjects\\UA1_A2\\out\\production\\UA1_A2";

            // Se crea un proceso para ejecutar LectorCadena
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", ruta, "LectorCadena");
            pb.inheritIO(); // Usa la misma consola (entrada y salida compartida)

            // Se inicia el proceso
            Process proceso = pb.start();

            // Espera a que el proceso termine antes de continuar
            proceso.waitFor();

        } catch (IOException e) {
            // Controla errores si no se puede ejecutar el proceso
            System.out.println("Error de entrada/salida al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            // Controla errores si el proceso es interrumpido
            System.out.println("El proceso fue interrumpido: " + e.getMessage());
        } catch (Exception e) {
            // Controla cualquier otro error inesperado
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }
}
