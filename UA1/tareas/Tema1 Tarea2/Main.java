import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

// Programa que ejecuta LectorCaracteres como un proceso separado
public class Main {

    public static void main(String[] args) {
        Process proceso = null;
        BufferedWriter escritor = null;
        BufferedReader lectorSalida = null;
        BufferedReader lectorError = null;

        System.out.println("=== EJECUTOR DE PROCESO ===");
        System.out.println("Iniciando el programa LectorCaracteres...\n");

        try {
            // FR3: Crear y ejecutar el proceso
            // Obtener el classpath actual (donde están compiladas las clases)
            String classpath = System.getProperty("java.class.path");

            ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, "LectorCaracteres");

            // Configurar el directorio de trabajo
            pb.directory(new File(System.getProperty("user.dir")));

            // Iniciar el proceso
            proceso = pb.start();
            System.out.println("Proceso iniciado correctamente (PID: " +
                    obtenerPID(proceso) + ")\n");

            // Configurar los streams de comunicación
            escritor = new BufferedWriter(
                    new OutputStreamWriter(proceso.getOutputStream()));
            lectorSalida = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));
            lectorError = new BufferedReader(
                    new InputStreamReader(proceso.getErrorStream()));

            // Enviar datos de prueba al proceso
            String textoPrueba = "Hola mundo desde el proceso padre.\n" +
                    "Esta es una segunda línea.\n" +
                    "Y aquí termina el texto*";

            System.out.println("Enviando datos al proceso:");
            System.out.println("\"" + textoPrueba + "\"\n");

            escritor.write(textoPrueba);
            escritor.flush();
            escritor.close(); // Cerrar para indicar fin de entrada

            // Leer y mostrar la salida del proceso
            System.out.println("=== SALIDA DEL PROCESO ===");
            String linea;
            boolean hayOutput = false;

            while ((linea = lectorSalida.readLine()) != null) {
                System.out.println(linea);
                hayOutput = true;
            }

            if (!hayOutput) {
                System.out.println("(El proceso no generó ninguna salida)");
            }

            // Verificar si hubo errores
            System.out.println("\n=== ERRORES DEL PROCESO ===");
            boolean hayErrores = false;

            while ((linea = lectorError.readLine()) != null) {
                System.err.println(linea);
                hayErrores = true;
            }

            if (!hayErrores) {
                System.out.println("(No se produjeron errores)");
            }

            // Esperar a que el proceso termine y obtener código de salida
            int codigoSalida = proceso.waitFor();

            System.out.println("\n=== INFORMACIÓN DEL PROCESO ===");
            System.out.println("Codigo de salida: " + codigoSalida);

            if (codigoSalida == 0) {
                System.out.println("Estado: Finalizado correctamente");
            } else {
                System.out.println("Estado: Finalizado con errores");
            }

        } catch (IOException e) {
            // Control de errores: Problemas de entrada/salida
            System.err.println("\nERROR DE E/S: No se pudo ejecutar el proceso.");
            System.err.println("Causa posible: " + e.getMessage());
            System.err.println("\nVerifique que:");
            System.err.println("1. El archivo LectorCaracteres.class existe");
            System.err.println("2. Java está correctamente instalado");
            System.err.println("3. Tiene permisos de ejecución");
            e.printStackTrace();

        } catch (InterruptedException e) {
            // Control de errores: Interrupción del hilo
            System.err.println("\nERROR: El proceso fue interrumpido.");
            System.err.println("Detalles: " + e.getMessage());
            Thread.currentThread().interrupt();
            e.printStackTrace();

        } catch (Exception e) {
            // Control de errores: Excepciones no previstas
            System.err.println("\nERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // Limpieza de recursos
            cerrarRecursos(escritor, lectorSalida, lectorError);

            // Destruir el proceso si aún está activo
            if (proceso != null && proceso.isAlive()) {
                System.out.println("\nDestruyendo proceso...");
                proceso.destroy();

                try {
                    // Esperar hasta 5 segundos para terminación normal
                    if (!proceso.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)) {
                        System.out.println("Forzando terminación del proceso...");
                        proceso.destroyForcibly();
                    }
                } catch (InterruptedException e) {
                    proceso.destroyForcibly();
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println("\n=== Programa ejecutor finalizado ===");
    }

    // Cierra los recursos de forma segura
    private static void cerrarRecursos(AutoCloseable... recursos) {
        for (AutoCloseable recurso : recursos) {
            if (recurso != null) {
                try {
                    recurso.close();
                } catch (Exception e) {
                    System.err.println("Advertencia: Error al cerrar recurso - " +
                            e.getMessage());
                }
            }
        }
    }

    // Intenta obtener el PID del proceso (Java 9+)
    private static String obtenerPID(Process proceso) {
        try {
            // Disponible desde Java 9
            return String.valueOf(proceso.pid());
        } catch (Exception e) {
            return "desconocido";
        }
    }
}