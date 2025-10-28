import java.io.*;

public class Main {

    public static void main(String[] args) {
        Process proceso = null;
        BufferedWriter salida = null;
        BufferedReader entrada = null;
        BufferedReader errores = null;
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("=== EJECUTOR DE PROCESO ===");
        System.out.println("Iniciando LectorCaracteres...\n");

        try {
            String classPath = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", classPath, "LectorCaracteres");
            pb.directory(new File(System.getProperty("user.dir")));
            proceso = pb.start();

            System.out.println("Proceso iniciado. PID: " + obtenerPID(proceso) + "\n");

            salida = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()));
            entrada = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            errores = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));

            System.out.println("Ingrese texto para enviar al proceso hijo (finalice con '*'):");
            String linea;
            while ((linea = teclado.readLine()) != null) {
                salida.write(linea + "\n");
                salida.flush();
                if (linea.contains("*")) break;
            }
            salida.close(); // Fin de la entrada

            System.out.println("\n=== SALIDA DEL PROCESO ===");
            String out;
            boolean haySalida = false;
            while ((out = entrada.readLine()) != null) {
                System.out.println(out);
                haySalida = true;
            }
            if (!haySalida) System.out.println("(Sin salida)");

            System.out.println("\n=== ERRORES DEL PROCESO ===");
            String err;
            boolean hayErrores = false;
            while ((err = errores.readLine()) != null) {
                System.err.println(err);
                hayErrores = true;
            }
            if (!hayErrores) System.out.println("(Sin errores)");

            int codigo = proceso.waitFor();
            System.out.println("\n=== ESTADO DEL PROCESO ===");
            System.out.println("Código de salida: " + codigo);
            System.out.println(codigo == 0 ? "Ejecución correcta" : "Finalizado con errores");

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Proceso interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            cerrar(salida, entrada, errores);
            if (proceso != null && proceso.isAlive()) {
                proceso.destroy();
            }
        }

        System.out.println("\n=== FIN DEL EJECUTOR ===");
    }

    private static void cerrar(AutoCloseable... recursos) {
        for (AutoCloseable r : recursos) {
            if (r != null) try { r.close(); } catch (Exception e) {
                System.err.println("Error al cerrar recurso: " + e.getMessage());
            }
        }
    }

    private static String obtenerPID(Process p) {
        try { return String.valueOf(p.pid()); } catch (Exception e) { return "N/A"; }
    }
}
