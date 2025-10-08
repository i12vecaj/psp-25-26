import java.io.*;
import java.util.*;

public class ProcessSimulator {
    public static void main(String[] args) {
        try {
            new File("logs").mkdirs();
            PrintWriter log = new PrintWriter(new FileWriter("logs/resultados_multiproceso.txt"));


            String classPath = System.getProperty("java.class.path");


            log.println("Ejecución secuencial:");
            long inicioSec = System.currentTimeMillis();
            ejecutar("Script1", classPath, log);
            ejecutar("Script2", classPath, log);
            ejecutar("Script3", classPath, log);
            long finSec = System.currentTimeMillis();
            log.println("Tiempo total secuencial: " + (finSec - inicioSec) + " ms\n");


            log.println("Ejecución paralela:");
            long inicioPar = System.currentTimeMillis();
            List<Process> procesos = new ArrayList<>();
            procesos.add(new ProcessBuilder("java", "-cp", classPath, "Script1").inheritIO().start());
            procesos.add(new ProcessBuilder("java", "-cp", classPath, "Script2").inheritIO().start());
            procesos.add(new ProcessBuilder("java", "-cp", classPath, "Script3").inheritIO().start());
            for (Process p : procesos) p.waitFor();
            long finPar = System.currentTimeMillis();
            log.println("Tiempo total paralelo: " + (finPar - inicioPar) + " ms\n");

            log.close();
            System.out.println("✅ Resultados guardados en logs/resultados_multiproceso.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ejecutar(String clase, String classPath, PrintWriter log) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", classPath, clase);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                log.println("[" + clase + "] " + linea);
            }
        }
        p.waitFor();
    }
}