import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProcessSimulator {

    public static void main(String[] args) {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = "out/production/Multiproceso";

        List<List<String>> comandos = Arrays.asList(
                Arrays.asList(javaBin, "-cp", classpath, "multiproceso.ScriptA"),
                Arrays.asList(javaBin, "-cp", classpath, "multiproceso.ScriptB"),
                Arrays.asList(javaBin, "-cp", classpath, "multiproceso.ScriptC")
        );

        Path logDir = Paths.get("logs");
        try {
            // Crea el directorio (lanza IOException si falla)
            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }
        } catch (IOException e) {
            System.err.println("No se pudo crear 'logs': " + e.getMessage());
            e.printStackTrace();
            return;
        }

        Path logFile = logDir.resolve("resultados_multiproceso.txt");

        // Ejecucion secuencial
        long inicioSec = System.currentTimeMillis();
        for (List<String> cmd : comandos) {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.inheritIO();
            try {
                Process p = pb.start();
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                escribirLog(logFile, "Error en ejecución secuencial: " + e.getMessage());
                e.printStackTrace();
                if (e instanceof InterruptedException) Thread.currentThread().interrupt();
            }
        }
        long finSec = System.currentTimeMillis();
        long tiempoSec = finSec - inicioSec;

        // Ejecución paralela
        long inicioPar = System.currentTimeMillis();
        List<Process> procesos = new ArrayList<>();
        for (List<String> cmd : comandos) {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.inheritIO();
            try {
                procesos.add(pb.start());
            } catch (IOException e) {
                escribirLog(logFile, "Error arrancando proceso paralelo: " + e.getMessage());
                e.printStackTrace();
            }
        }
        for (Process p : procesos) {
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                escribirLog(logFile, "Interrupción esperando procesos: " + e.getMessage());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        long finPar = System.currentTimeMillis();
        long tiempoPar = finPar - inicioPar;

        // Guardado de los resultados
        try (BufferedWriter w = Files.newBufferedWriter(logFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write("===== RESULTADOS DE EJECUCIÓN =====\n");
            w.write("Tiempo total secuencial: " + tiempoSec + " ms\n");
            w.write("Tiempo total paralelo:   " + tiempoPar + " ms\n");
            w.write("Ahorro aproximado:       " + (tiempoSec - tiempoPar) + " ms\n");
            w.write("-------------------------------------\n");
        } catch (IOException e) {
            System.err.println("No se pudo escribir el log: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(" Resultados guardados en " + logFile.toString());
    }

    private static void escribirLog(Path logFile, String texto) {
        try {
            Files.writeString(logFile, texto + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.err.println("No se pudo escribir en log: " + ex.getMessage());
        }
    }
}