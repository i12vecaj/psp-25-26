import java.io.IOException;

public class Ejecutor {

    // Método que ejecuta el programa "Main" con o sin argumentos
    private static int ejecutarPrincipal(String argumento) {
        Process proceso = null;         // Objeto para representar el proceso que ejecutará "Main"
        int codigoSalida = -1;          // Almacena el código de salida devuelto por "Main"
        String[] comando;               // Array que contendrá el comando a ejecutar

        // Si no se pasa argumento, se ejecuta el programa sin parámetros
        if (argumento == null) {
            comando = new String[] { "java", "-cp", ".", "Main" };
            System.out.println("\n--- Ejecutando Main sin argumentos ---");
        } else { // Si hay argumento, se incluye en el comando
            comando = new String[] { "java", "-cp", ".", "Main", argumento };
            System.out.println("\n--- Ejecutando Main con argumento: '" + argumento + "' ---");
        }

        try {
            // Ejecuta el comando en un nuevo proceso del sistema
            proceso = Runtime.getRuntime().exec(comando);

            // Espera a que el proceso termine y obtiene su código de salida
            codigoSalida = proceso.waitFor();

            // Muestra el código de salida devuelto por la ejecución de "Main"
            System.out.println("Código de Salida devuelto por Main: " + codigoSalida);

            // Dependiendo del código de salida, muestra el resultado correspondiente
            switch (codigoSalida) {
                case 1:
                    System.out.println("Resultado: ERROR: No se proporcionó ningún argumento.");
                    break;
                case 2:
                    System.out.println("Resultado: Argumento no es un número entero.");
                    break;
                case 3:
                    System.out.println("Resultado: Argumento es un número entero negativo.");
                    break;
                case 0:
                    System.out.println("Resultado: Argumento válido (entero >= 0).");
                    break;
                default:
                    System.out.println("Resultado: Código de salida desconocido.");
            }

            // Captura errores relacionados con entrada/salida (por ejemplo, si no se encuentra la clase Main)
        } catch (IOException e) {
            System.err.println("Error de I/O al ejecutar el proceso: " + e.getMessage());

            // Captura el caso en que el proceso sea interrumpido mientras se espera su finalización
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());

            // Finalmente, asegura que el proceso se cierre correctamente
        } finally {
            if (proceso != null) {
                proceso.destroy();
            }
        }

        // Devuelve el código de salida obtenido
        return codigoSalida;
    }

    // Método principal del programa
    public static void main(String[] args) {
        // Llama al método anterior varias veces para probar distintos escenarios
        ejecutarPrincipal(null);       // Caso sin argumento
        ejecutarPrincipal("Prueba");   // Caso con una cadena no numérica
        ejecutarPrincipal("-9");       // Caso con número negativo
        ejecutarPrincipal("8");        // Caso con número entero positivo
        ejecutarPrincipal("0");        // Caso con número cero
    }
}
