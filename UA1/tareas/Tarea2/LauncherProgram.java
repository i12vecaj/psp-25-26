import java.io.IOException;
/**
 * Programa lanzador (proceso padre) que ejecuta el ReaderProgram como proceso hijo.
 * Funcionalidades:
 *  - FR3: Crear otro programa que ejecute al primero.
 *  - Control de errores y reporte del código de salida del proceso hijo.
 * Uso:
 *  javac ReaderProgram.java LauncherProgram.java
 *  java LauncherProgram
 */
public class LauncherProgram {
    public static void main(String[] args) {
        System.out.println("[LAUNCHER] Iniciando lanzamiento del proceso lector...");
        ProcessBuilder pb = new ProcessBuilder("java", "ReaderProgram");
        pb.inheritIO();
        Process process = null;
        try {
            process = pb.start();
        } catch (IOException ioe) {
            System.err.println("[LAUNCHER][ERROR] No se pudo iniciar el proceso hijo: " + ioe.getMessage());
            System.exit(5);
        } catch (SecurityException se) {
            System.err.println("[LAUNCHER][ERROR] Restricción de seguridad al iniciar el proceso: " + se.getMessage());
            System.exit(6);
        }
        int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("[LAUNCHER][ERROR] Hilo interrumpido mientras esperaba al proceso hijo.");
            System.exit(7);
            return;
        }
        if (exitCode == 0) {
            System.out.println("[LAUNCHER] Proceso hijo finalizado correctamente (exitCode=0).");
        } else {
            System.err.println("[LAUNCHER][WARN] Proceso hijo terminó con código de salida: " + exitCode);
        }
    }
}