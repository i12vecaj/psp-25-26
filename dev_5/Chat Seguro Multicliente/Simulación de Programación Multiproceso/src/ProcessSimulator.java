
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProcessSimulator {

    static class StreamGobbler implements Runnable {
        private final InputStream is;
        private final String prefix;
        private final StringWriter out = new StringWriter();
        StreamGobbler(InputStream is, String prefix) { this.is = is; this.prefix = prefix; }
        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    out.append(prefix).append(line).append(System.lineSeparator());
                }
            } catch (IOException e) {
                out.append(prefix).append("ERROR reading stream: ").append(e.toString()).append(System.lineSeparator());
            }
        }
        public String getOutput() { return out.toString(); }
    }

    public static void main(String[] args) throws Exception {
        Path logsDir = Paths.get("logs");
        if (!Files.exists(logsDir)) Files.createDirectories(logsDir);
        Path logFile = logsDir.resolve("resultados_multiproceso.txt");

   
        List<String[]> commands = Arrays.asList(
            new String[]{"java", "-cp", ".", "Worker", "A", "5"},
            new String[]{"java", "-cp", ".", "Worker", "B", "3"},
            new String[]{"java", "-cp", ".", "Worker", "C", "4"}
        );

        StringBuilder log = new StringBuilder();
        log.append("=== Simulación multiproceso ===").append(System.lineSeparator());

        // Parallel run
        log.append(System.lineSeparator()).append("** PARALLEL RUN **").append(System.lineSeparator());
        long pStart = System.currentTimeMillis();
        List<Process> procs = new ArrayList<>();
        List<Thread> gobblers = new ArrayList<>();
        List<StreamGobbler> gobblersObj = new ArrayList<>();

        for (int i = 0; i < commands.size(); i++) {
            String[] cmd = commands.get(i);
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process proc = pb.start();
            procs.add(proc);

            StreamGobbler g = new StreamGobbler(proc.getInputStream(), "[" + (i+1) + "] ");
            Thread t = new Thread(g);
            t.start();
            gobblers.add(t);
            gobblersObj.add(g);
        }


        for (Process p : procs) p.waitFor();
        for (Thread t : gobblers) t.join();

        long pEnd = System.currentTimeMillis();
        long pDuration = pEnd - pStart;
        log.append("Parallel elapsed (ms): ").append(pDuration).append(System.lineSeparator());
        for (int i = 0; i < gobblersObj.size(); i++) {
            log.append("--- Output proceso ").append(i+1).append(" ---").append(System.lineSeparator());
            log.append(gobblersObj.get(i).getOutput()).append(System.lineSeparator());
        }

        // Sequential run
        log.append(System.lineSeparator()).append("** SEQUENTIAL RUN **").append(System.lineSeparator());
        long sStart = System.currentTimeMillis();
        int idx = 0;
        for (String[] cmd : commands) {
            idx++;
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process proc = pb.start();

            StreamGobbler g = new StreamGobbler(proc.getInputStream(), "[S" + idx + "] ");
            Thread t = new Thread(g);
            t.start();

            proc.waitFor();
            t.join();

            log.append("--- Output sequential proceso ").append(idx).append(" ---").append(System.lineSeparator());
            log.append(g.getOutput()).append(System.lineSeparator());
        }
        long sEnd = System.currentTimeMillis();
        long sDuration = sEnd - sStart;
        log.append("Sequential elapsed (ms): ").append(sDuration).append(System.lineSeparator());

        log.append(System.lineSeparator()).append("=== Resumen ===").append(System.lineSeparator());
        log.append("Tiempo PARALLEL (ms): ").append(pDuration).append(System.lineSeparator());
        log.append("Tiempo SEQUENTIAL (ms): ").append(sDuration).append(System.lineSeparator());
        log.append("Ahorro aproximado (ms): ").append(sDuration - pDuration).append(System.lineSeparator());

        Files.write(logFile, log.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Log escrito en: " + logFile.toAbsolutePath());
        System.out.println("Parallel ms: " + pDuration + " | Sequential ms: " + sDuration);
    }
}