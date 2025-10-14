import java.io.IOException;

/**
 * Clase que ejecuta a Principal.java y maneja su código de salida.
 */

public class Ejecutor {

    public static void main(String[] args) {

        // Obtiene el argumento pasado al Ejecutor.
        String argumento = "10";

        // Llama al metodo que inicia el proceso Principal y obtiene el resultado.
        int resultado = ejecutarPrincipal(argumento);

        // Muestra un mensaje basado en el código devuelto por la clase Principal
        System.out.println("\n--- ANÁLISIS DEL CÓDIGO DE SALIDA (" + resultado + ") ---");
        switch (resultado) {
            case 1:
                System.out.println("Codigo 1. Error: No se proporcionó ningún argumento.");
                break;
            case 2:
                System.out.println("Codigo 2. Error: El argumento no es un número entero.");
                break;
            case 3:
                System.out.println("Codigo 3. Error: El número proporcionado es negativo.");
                break;
            case 0:
                System.out.println("Codigo 0. El argumento es correcto (entero no negativo).");
                break;
            default:
                // Caso para códigos de error internos (como -1).
                System.out.println("Codigo inesperado: " + resultado + ". (Fallo en la ejecución del proceso).");
                break;
        }
    }

    //Inicia la clase Principal como un proceso externo (hijo).
    public static int ejecutarPrincipal(String argumento) {
        try {
            // Obtiene la ruta actual de las clases para que el proceso hijo encuentre la clase Principal
            String classpath = System.getProperty("java.class.path");

            ProcessBuilder pb;
            // Define el comando a ejecutar
            if (argumento == null) {
                pb = new ProcessBuilder("java", "-cp", classpath, "Principal");
            } else {
                pb = new ProcessBuilder("java", "-cp", classpath, "Principal", argumento);
            }

            // Redirige la salida estándar y de error del hijo al padre
            pb.inheritIO();

            // Inicia la ejecución del proceso.
            Process proceso = pb.start();

            // Espera a que el proceso Principal termine.
            proceso.waitFor();

            // Devuelve el código que el proceso Principal devolvió con System.exit()
            return proceso.exitValue();

        } catch (IOException e) {
            // Control de errores de E/S
            System.err.println("\nFallo al ejecutar el proceso. Mensaje: " + e.getMessage());
            return -1;
        } catch (InterruptedException e) {
            // Control de errores de interrupción
            System.err.println("\nEl Ejecutor fue interrumpido.");
            Thread.currentThread().interrupt();
            return -1;
        }
    }
}