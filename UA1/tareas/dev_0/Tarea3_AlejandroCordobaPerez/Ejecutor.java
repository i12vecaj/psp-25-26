import java.io.IOException;

/**
 * Clase Ejecutor.
 * Ejecuta el programa Principal.java con diferentes argumentos y muestra por
 * pantalla lo que sucede en función del código de salida devuelto por el
 * proceso hijo.
 *
 * NOTA: Para que este programa funcione correctamente, ambos archivos (.java y
 * .class)
 * deben estar en el mismo directorio y compilados.
 */
public class Ejecutor {

    /**
     * Ejecuta el programa Principal con un argumento dado.
     * 
     * @param argumento El argumento a pasar al programa Principal.
     * @return El código de salida devuelto por el programa Principal.
     */
    private static int ejecutarPrincipal(String argumento) {
        Process proceso = null;
        int codigoSalida = -1;
        String[] comando;

        // Si el argumento es null, ejecutamos sin argumentos
        if (argumento == null) {
            comando = new String[] { "java", "Main" };
            System.out.println("\n--- Ejecutando Main sin argumentos ---");
        } else {
            comando = new String[] { "java", "Main", argumento };
            System.out.println("\n--- Ejecutando Main con argumento: '" + argumento + "' ---");
        }

        try {
            // FR3: Crea después otro programa que ejecute el anterior
            // Ejecución del proceso hijo (Main.java)
            proceso = Runtime.getRuntime().exec(comando);

            // Espera a que el proceso termine
            codigoSalida = proceso.waitFor();

            // Muestra el código de salida devuelto
            System.out.println("Código de Salida devuelto por Main: " + codigoSalida);

            // Analiza el código de salida (Implementación de control de errores)
            switch (codigoSalida) {
                case 1:
                    System.out.println("Resultado: ERROR: No se proporcionó ningún argumento.");
                    break;
                case 2:
                    System.out.println("Resultado: Argumento no es un número entero (es una cadena/otro formato).");
                    break;
                case 3:
                    System.out.println("Resultado: Argumento es un número entero negativo (< 0).");
                    break;
                case 0:
                    System.out.println("Resultado: Argumento válido (entero >= 0).");
                    break;
                default:
                    System.out.println("Resultado: Código de salida desconocido.");
            }

        } catch (IOException e) {
            // Control de errores si falla la ejecución del comando (p. ej. 'java' no
            // encontrado)
            System.err.println("Error de I/O al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            // Control de errores si el hilo actual es interrumpido mientras espera
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
        } finally {
            if (proceso != null) {
                proceso.destroy(); // Asegura la liberación de recursos del proceso
            }
        }
        return codigoSalida;
    }

    /**
     * Punto de entrada para el programa Ejecutor.
     * 
     * @param args No se utilizan argumentos en este programa.
     */
    public static void main(String[] args) {
        // Ejecución con diferentes casos de prueba
        ejecutarPrincipal(null); // Caso 1: sin argumentos (devuelve 1)
        ejecutarPrincipal("Hola"); // Caso 2: argumento cadena (devuelve 2)
        ejecutarPrincipal("-5"); // Caso 3: argumento entero negativo (devuelve 3)
        ejecutarPrincipal("10"); // Caso 4: argumento entero positivo (devuelve 0)
        ejecutarPrincipal("0"); // Caso 5: argumento entero cero (devuelve 0)
    }
}
