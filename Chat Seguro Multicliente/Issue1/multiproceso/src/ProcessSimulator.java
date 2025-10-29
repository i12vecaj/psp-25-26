import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProcessSimulator {

    private static final Path BASE_DIR = Paths.get("Chat Seguro Multicliente","Issue1","multiproceso");
    private static final Path LOG_DIR = BASE_DIR.resolve("logs");
    private static final Path LOG_FILE = LOG_DIR.resolve("resultados_multiproceso.txt");
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat DF = new DecimalFormat("#,##0.00");

    public static void main(String[] args) throws Exception {
        ensureLogDir();

        List<List<String>> comandos = List.of(
                buildJavaCommand("tasks.TaskLento"),
                buildJavaCommand("tasks.TaskIO"),
                buildJavaCommand("tasks.TaskCPU")
        );

        appendSection("=== EJECUCIÓN PARALELA ===");
        long parallelStart = System.nanoTime();
        List<ProcesoResultado> resultadosParalelo = ejecutarEnParalelo(comandos);
        long parallelElapsed = System.nanoTime() - parallelStart;
        appendLine("Tiempo total paralelo: " + formatMillis(parallelElapsed));
        appendLine("");

        appendSection("=== EJECUCIÓN SECUENCIAL ===");
        long seqStart = System.nanoTime();
        List<ProcesoResultado> resultadosSecuencial = ejecutarSecuencial(comandos);
        long seqElapsed = System.nanoTime() - seqStart;
        appendLine("Tiempo total secuencial: " + formatMillis(seqElapsed));
        appendLine("");

        appendSection("=== RESUMEN COMPARATIVO ===");
        double speedup = (double) seqElapsed / (double) parallelElapsed;
        double ahorroPct = (1 - (double) parallelElapsed / (double) seqElapsed) * 100.0;
        appendLine("Speedup: " + DF.format(speedup) + "x");
        appendLine("Ahorro estimado: " + DF.format(ahorroPct) + "%");
        appendLine("Fecha: " + TS.format(LocalDateTime.now()));
        appendLine("");

        appendSection("=== DETALLES POR PROCESO (PARALELO) ===");
        resultadosParalelo.forEach(ProcessSimulator::dumpResultado);

        appendSection("=== DETALLES POR PROCESO (SECUENCIAL) ===");
        resultadosSecuencial.forEach(ProcessSimulator::dumpResultado);

        System.out.println("Simulación completada. Log en: " + LOG_FILE.toAbsolutePath());
    }

    private static List<String> buildJavaCommand(String className) {
        return List.of("java", "-cp", "Chat Seguro Multicliente/Issue1/multiproceso/src", className);
    }

    private static List<ProcesoResultado> ejecutarEnParalelo(List<List<String>> comandos) throws InterruptedException {
        List<ProcesoResultado> resultados = new ArrayList<>();
        List<Process> processes = new ArrayList<>();
        List<StreamCollector> collectors = new ArrayList<>();

        for (List<String> cmd : comandos) {
            try {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.redirectErrorStream(false);
                Process p = pb.start();
                processes.add(p);
                StreamCollector outCol = new StreamCollector(p.getInputStream());
                StreamCollector errCol = new StreamCollector(p.getErrorStream());
                collectors.add(outCol);
                collectors.add(errCol);
                outCol.start();
                errCol.start();
                resultados.add(new ProcesoResultado(cmd));
            } catch (IOException e) {
                appendLine("ERROR lanzando comando: " + cmd + " -> " + e.getMessage());
            }
        }

        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);
            ProcesoResultado r = resultados.get(i);
            long start = System.nanoTime();
            try {
                int exit = p.waitFor();
                long elapsed = System.nanoTime() - start;
                r.exitCode = exit;
                r.elapsedNanos = elapsed;
                r.stdout = collectors.get(i * 2).getContenido();
                r.stderr = collectors.get(i * 2 + 1).getContenido();
            } catch (InterruptedException e) {
                r.stderr = "Interrumpido: " + e.getMessage();
            }
        }
        return resultados;
    }

    private static List<ProcesoResultado> ejecutarSecuencial(List<List<String>> comandos) {
        List<ProcesoResultado> resultados = new ArrayList<>();
        for (List<String> cmd : comandos) {
            ProcesoResultado r = new ProcesoResultado(cmd);
            long start = System.nanoTime();
            try {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                Process p = pb.start();
                String stdout = readStream(p.getInputStream());
                String stderr = readStream(p.getErrorStream());
                int exit = p.waitFor();
                r.exitCode = exit;
                r.stdout = stdout;
                r.stderr = stderr;
                r.elapsedNanos = System.nanoTime() - start;
            } catch (Exception e) {
                r.stderr = "Fallo: " + e.getMessage();
            }
            resultados.add(r);
        }
        return resultados;
    }

    private static void dumpResultado(ProcesoResultado r) {
        appendLine("Comando: " + String.join(" ", r.comando));
        appendLine("Exit code: " + r.exitCode);
        appendLine("Tiempo: " + formatMillis(r.elapsedNanos));
        if (!r.stdout.isBlank()) {
            appendLine("--- STDOUT ---");
            appendLine(r.stdout.strip());
        }
        if (!r.stderr.isBlank()) {
            appendLine("--- STDERR ---");
            appendLine(r.stderr.strip());
        }
        appendLine("----------------------------------------");
    }

    private static String formatMillis(long nanos) {
        return DF.format(nanos / 1_000_000.0) + " ms";
    }

    private static void ensureLogDir() throws IOException {
        if (!Files.exists(LOG_DIR)) {
            Files.createDirectories(LOG_DIR);
        }
        if (!Files.exists(LOG_FILE)) {
            Files.createFile(LOG_FILE);
        }
    }

    private static synchronized void appendSection(String title) { appendLine(title); }

    private static synchronized void appendLine(String line) {
        try (BufferedWriter bw = Files.newBufferedWriter(LOG_FILE, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error escribiendo log: " + e.getMessage());
        }
    }

    private static String readStream(InputStream in) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String l;
            while ((l = br.readLine()) != null) {
                sb.append(l).append(System.lineSeparator());
            }
            return sb.toString();
        }
    }

    private static class StreamCollector extends Thread {
        private final InputStream in;
        private final StringBuilder sb = new StringBuilder();

        StreamCollector(InputStream in) { this.in = in; }

        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String l;
                while ((l = br.readLine()) != null) {
                    sb.append(l).append(System.lineSeparator());
                }
            } catch (IOException ignored) { }
        }

        String getContenido() { return sb.toString(); }
    }

    private static class ProcesoResultado {
        List<String> comando;
        String stdout = "";
        String stderr = "";
        int exitCode = -1;
        long elapsedNanos;
        ProcesoResultado(List<String> comando) { this.comando = comando; }
    }
}