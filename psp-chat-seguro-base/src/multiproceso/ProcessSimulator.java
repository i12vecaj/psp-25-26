package multiproceso;
import java.io.*;
import java.util.*;
import java.time.*;

public class ProcessSimulator {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] commands = {
                "java script1.java",
                "java script2.java",
                "java script3.java"
        };

        // Ejecución secuencial
        long startSeq = System.currentTimeMillis();
        for (String cmd : commands) {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        }
        long endSeq = System.currentTimeMillis();

        // Ejecución paralela
        long startPar = System.currentTimeMillis();
        List<Process> processes = new ArrayList<>();
        for (String cmd : commands) {
            processes.add(new ProcessBuilder(cmd.split(" ")).start());
        }
        for (Process p : processes) {
            p.waitFor();
        }
        long endPar = System.currentTimeMillis();

        // Guardar resultados
        File log = new File("C:\\Users\\rafam\\OneDrive\\Escritorio\\2 dam\\Programación de Servicios y Procesos\\psp-25-26\\psp-chat-seguro-base\\logs\\resultados_multiproceso.txt");
        log.getParentFile().mkdirs();
        try (PrintWriter writer = new PrintWriter(log)) {
            writer.println("Tiempo secuencial: " + (endSeq - startSeq) + " ms");
            writer.println("Tiempo paralelo: " + (endPar - startPar) + " ms");
        }

        System.out.println("Resultados guardados en " + log.getPath());
    }
}
