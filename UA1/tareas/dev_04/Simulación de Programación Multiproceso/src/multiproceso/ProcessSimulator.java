package src.multiproceso;
import java.io.*;
import java.util.*;

public class ProcessSimulator {

    private static void log(String text, String filename) {
        try (FileWriter fw = new FileWriter(filename, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ejecutarSecuencial(String logFile) throws Exception {
        long inicio = System.currentTimeMillis();

        ProcessBuilder pb1 = new ProcessBuilder("java", "Script1");
        pb1.inheritIO().start().waitFor();

        ProcessBuilder pb2 = new ProcessBuilder("java", "Script2");
        pb2.inheritIO().start().waitFor();

        ProcessBuilder pb3 = new ProcessBuilder("java", "Script3");
        pb3.inheritIO().start().waitFor();

        long fin = System.currentTimeMillis();
        long tiempo = fin - inicio;
        log("Ejecución secuencial: " + tiempo + " ms", logFile);
    }

    private static void ejecutarParalelo(String logFile) throws Exception {
        long inicio = System.currentTimeMillis();

        List<Process> procesos = new ArrayList<>();
        procesos.add(new ProcessBuilder("java", "Script1").inheritIO().start());
        procesos.add(new ProcessBuilder("java", "Script2").inheritIO().start());
        procesos.add(new ProcessBuilder("java", "Script3").inheritIO().start());

        // Esperar a que todos terminen
        for (Process p : procesos) {
            p.waitFor();
        }

        long fin = System.currentTimeMillis();
        long tiempo = fin - inicio;
        log("Ejecución paralela: " + tiempo + " ms", logFile);
    }

    public static void main(String[] args) throws Exception {
        String logFile = "logs/resultados_multiproceso.txt";

        File f = new File(logFile);
        f.getParentFile().mkdirs();
        f.createNewFile();

        log("---- Resultados Multiproceso ----", logFile);
        ejecutarSecuencial(logFile);
        ejecutarParalelo(logFile);
        log("-------------------------------", logFile);

        System.out.println("Resultados guardados en " + logFile);
    }
}
