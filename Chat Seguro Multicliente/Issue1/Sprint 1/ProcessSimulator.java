import java.io.*;
import java.util.*;

public class ProcessSimulator {
    public static void main(String[] args) {
        try {
            new File("logs").mkdirs(); // Asegura carpeta de logs
            FileWriter log = new FileWriter("logs/resultados_multiproceso.txt", false);

            log.write("=== Simulación de Procesos ===\n\n");

            // --- Ejecución secuencial ---
            long startSeq = System.currentTimeMillis();
            ejecutarProceso("java", "ScriptA");
            ejecutarProceso("java", "ScriptB");
            ejecutarProceso("java", "ScriptC");
            long endSeq = System.currentTimeMillis();

            long tiempoSecuencial = endSeq - startSeq;
            log.write("Tiempo secuencial: " + tiempoSecuencial + " ms\n");

            // --- Ejecución paralela ---
            long startPar = System.currentTimeMillis();
            Process p1 = new ProcessBuilder("java", "ScriptA").start();
            Process p2 = new ProcessBuilder("java", "ScriptB").start();
            Process p3 = new ProcessBuilder("java", "ScriptC").start();

            p1.waitFor();
            p2.waitFor();
            p3.waitFor();

            long endPar = System.currentTimeMillis();
            long tiempoParalelo = endPar - startPar;
            log.write("Tiempo paralelo: " + tiempoParalelo + " ms\n");

            // --- Resultado comparativo ---
            log.write("\nDiferencia: " + (tiempoSecuencial - tiempoParalelo) + " ms menos con paralelismo.\n");

            log.close();
            System.out.println("Simulación completada. Revisa logs/resultados_multiproceso.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ejecutarProceso(String... comando) throws IOException, InterruptedException {
        Process proceso = new ProcessBuilder(comando).inheritIO().start();
        proceso.waitFor();
    }
}
