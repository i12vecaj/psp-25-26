import java.io.IOException;

/**
 * Programa EjecutarPrincipal.java
 * 
 * Funcionalidades:
 *  - Ejecuta el programa Principal.java.
 *  - Muestra un mensaje según el valor devuelto por el programa ejecutado.
 * 
 * Control de errores implementado.
 */
public class ejecutarprincipal {
    public static void main(String[] args) {
        try {
            // Construir el comando para ejecutar el programa Principal
            // (Asumiendo que Principal.class ya fue compilado)
            Process proceso = Runtime.getRuntime().exec("java Principal " + String.join(" ", args));

            // Esperar a que el proceso termine y obtener el valor de salida
            int codigoSalida = proceso.waitFor();

            // Mostrar resultado según el código de salida
            switch (codigoSalida) {
                case 0:
                    System.out.println("Ejecución correcta: número válido.");
                    break;
                case 1:
                    System.out.println("Error: no se ha proporcionado ningún argumento.");
                    break;
                case 2:
                    System.out.println("Error: el argumento no es un número entero.");
                    break;
                case 3:
                    System.out.println("Error: el número es negativo.");
                    break;
                default:
                    System.out.println("Error desconocido. Código devuelto: " + codigoSalida);
                    break;
            }

        } catch (IOException e) {
            System.err.println("Error al intentar ejecutar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("La ejecución del programa fue interrumpida.");
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}
