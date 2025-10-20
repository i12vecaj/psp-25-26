import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProcessSimulator {

    public static void main(String[] args) {
        try {
            System.out.println("=== SIMULACIÓN DE PROGRAMACIÓN MULTIPROCESO ===\n");

            // Ruta de compilación
            String classPath = "C:\\Users\\Rafa\\IdeaProjects\\ChatSeguroMulticliente\\out\\production\\ChatSeguroMulticliente";

            // Lista de procesos a ejecutar (sin paquete)
            List<ProcessBuilder> procesos = Arrays.asList(
                    new ProcessBuilder("java", "-cp", classPath, "ScriptA"),
                    new ProcessBuilder("java", "-cp", classPath, "ScriptB"),
                    new ProcessBuilder("java", "-cp", classPath, "ScriptC")
            );

            // Ejecución secuencial
            long inicioSecuencial = System.currentTimeMillis();
            System.out.println(" Ejecución secuencial...\n");
            for (ProcessBuilder pb : procesos) ejecutarYMostrar(pb);
            long tiempoSecuencial = System.currentTimeMillis() - inicioSecuencial;
            System.out.println("\n Tiempo total secuencial: " + tiempoSecuencial + " ms\n");

            // Ejecución paralela
            long inicioParalelo = System.currentTimeMillis();
            System.out.println(" Ejecución en paralelo...\n");
            List<Process> procesosActivos = new ArrayList<>();
            for (ProcessBuilder pb : procesos) {
                pb.redirectErrorStream(true);
                procesosActivos.add(pb.start());
            }
            for (Process p : procesosActivos) {
                mostrarSalida(p);
                p.waitFor();
            }
            long tiempoParalelo = System.currentTimeMillis() - inicioParalelo;
            System.out.println("\n Tiempo total paralelo: " + tiempoParalelo + " ms");

            // Guardar resultados en log
            guardarLog(tiempoSecuencial, tiempoParalelo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ejecutarYMostrar(ProcessBuilder pb) throws IOException, InterruptedException {
        pb.redirectErrorStream(true);
        Process proceso = pb.start();
        mostrarSalida(proceso);
        proceso.waitFor();
    }

    private static void mostrarSalida(Process proceso) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("  " + linea);
            }
        }
    }

    private static void guardarLog(long secuencial, long paralelo) {
        try {
            Path dir = Paths.get("logs");
            if (!Files.exists(dir)) Files.createDirectory(dir);

            String contenido = String.format("""
                    ===== RESULTADOS DE SIMULACIÓN =====
                    Tiempo secuencial: %d ms
                    Tiempo paralelo:   %d ms
                    Diferencia:        %d ms (%.2fx más rápido)
                    =====================================
                    """, secuencial, paralelo, secuencial - paralelo, (double) secuencial / paralelo);

            Files.writeString(Paths.get("logs/resultados_multiproceso.txt"),
                    contenido,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("\nResultados guardados en logs/resultados_multiproceso.txt");

        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de log: " + e.getMessage());
        }
    }
}
