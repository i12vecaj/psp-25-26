import java.io.*;
import java.util.*;

public class ProcessSimulator {

    public static void main(String[] args) {
        try {
            // Crear carpeta de logs si no existe
            File logDir = new File("logs");
            if (!logDir.exists()) logDir.mkdirs();

            File logFile = new File("logs/resultados_multiproceso.txt");
            PrintWriter logWriter = new PrintWriter(new FileWriter(logFile, false));

            logWriter.println("=== SIMULACIÓN DE PROCESOS ===");
            logWriter.println("Fecha: " + new Date());
            logWriter.println("---------------------------------------");

            long startSequential = System.currentTimeMillis();
            runScript("Proceso1", 3000); // simula 3 segundos
            runScript("Proceso2", 2000); // simula 2 segundos
            runScript("Proceso3", 4000); // simula 4 segundos
            long endSequential = System.currentTimeMillis();

            long tiempoSecuencial = endSequential - startSequential;
            logWriter.println("Tiempo total SECUENCIAL: " + tiempoSecuencial + " ms");

            long startParallel = System.currentTimeMillis();
            List<Process> procesos = new ArrayList<>();
            procesos.add(lanzarProceso("Proceso1", 3000));
            procesos.add(lanzarProceso("Proceso2", 2000));
            procesos.add(lanzarProceso("Proceso3", 4000));

            for (Process p : procesos) {
                p.waitFor();
            }

            long endParallel = System.currentTimeMillis();
            long tiempoParalelo = endParallel - startParallel;

            logWriter.println("Tiempo total PARALELO: " + tiempoParalelo + " ms");
            logWriter.println("---------------------------------------");

            double mejora = (1 - ((double) tiempoParalelo / tiempoSecuencial)) * 100;
            logWriter.printf("Mejora aproximada: %.2f%%", mejora);

            logWriter.println("Fin de simulación.");
            logWriter.close();

            System.out.println("Simulación completada. Revisa el archivo: logs/resultados_multiproceso.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runScript(String nombre, int duracionMs) throws InterruptedException {
        System.out.println("Ejecutando " + nombre + "...");
        Thread.sleep(duracionMs); // simula trabajo
        System.out.println(nombre + " terminado.");
    }

    private static Process lanzarProceso(String nombre, int duracionMs) throws IOException {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        return new ProcessBuilder(javaBin, "-cp", ".", "multiproceso.ProcessSimulatorHelper", nombre, String.valueOf(duracionMs))
                .inheritIO() // para mostrar salida del subproceso
                .start();
    }
}

class ProcessSimulatorHelper {
    public static void main(String[] args) {
        String nombre = args[0];
        int duracion = Integer.parseInt(args[1]);
        try {
            System.out.println("[Subproceso] " + nombre + " iniciado.");
            Thread.sleep(duracion);
            System.out.println("[Subproceso] " + nombre + " finalizado.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}