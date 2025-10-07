import java.io.*;

public class ProcessSimulator {
    public static void main(String[] args) throws Exception {
        File logFile = new File("logs/resultados_multiproceso.txt");
        logFile.getParentFile().mkdirs();
        PrintWriter log = new PrintWriter(new FileWriter(logFile, false));

        // Secuencial
        long startSeq = System.currentTimeMillis();
        runProcess("java Script1");
        runProcess("java Script2");
        runProcess("java Script3");
        long endSeq = System.currentTimeMillis();
        long timeSeq = endSeq - startSeq;
        log.println("Tiempo secuencial: " + timeSeq + " ms");

        // Paralelo
        long startPar = System.currentTimeMillis();
        Process p1 = new ProcessBuilder("java", "Script1").start();
        Process p2 = new ProcessBuilder("java", "Script2").start();
        Process p3 = new ProcessBuilder("java", "Script3").start();
        p1.waitFor();
        p2.waitFor();
        p3.waitFor();
        long endPar = System.currentTimeMillis();
        long timePar = endPar - startPar;
        log.println("Tiempo paralelo: " + timePar + " ms");

        log.close();
        System.out.println("Resultados guardados en logs/resultados_multiproceso.txt");
    }

    private static void runProcess(String command) throws Exception {
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
    }
}
