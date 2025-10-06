import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessSimulator {

    public static void main(String[] args) {
        File logDir = new File("multiproceso/logs");
        logDir.mkdirs();

        File logFile = new File("multiproceso/logs/resultados_multiproceso.txt");

        try (FileWriter writer = new FileWriter(logFile)) {

            // --- Ejecución Secuencial ---
            long inicioSecuencial = System.currentTimeMillis();

            Process p1 = new ProcessBuilder("java", "Script1").start();
            p1.waitFor();

            Process p2 = new ProcessBuilder("java", "Script2").start();
            p2.waitFor();

            Process p3 = new ProcessBuilder("java", "Script3").start();
            p3.waitFor();

            long tiempoSecuencial = System.currentTimeMillis() - inicioSecuencial;
            writer.write("Tiempo total SECUENCIAL: " + tiempoSecuencial + " ms\n");

            // --- Ejecución Paralela ---
            long inicioParalelo = System.currentTimeMillis();

            List<Process> procesos = new ArrayList<>();
            procesos.add(new ProcessBuilder("java", "Script1").start());
            procesos.add(new ProcessBuilder("java", "Script2").start());
            procesos.add(new ProcessBuilder("java", "Script3").start());

            for (Process p : procesos) {
                p.waitFor();
            }

            long tiempoParalelo = System.currentTimeMillis() - inicioParalelo;
            writer.write("Tiempo total PARALELO: " + tiempoParalelo + " ms\n");

            writer.write("\nDiferencia: " + (tiempoSecuencial - tiempoParalelo) + " ms\n");

            System.out.println("Simulación completada. Resultados guardados en multiproceso/logs/resultados_multiproceso.txt");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
