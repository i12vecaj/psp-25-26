import java.io.*;
import java.util.concurrent.*;

public class ProcessSimulator {

    public static void main(String[] args) {
        //Lista de comandos a ejecutar
        // En Linux / Mac usa "bash -c"
        // En Windows cambia "bash -c" por "cmd.exe /c"
        String[][] comandos = {
                {"bash", "-c", "sleep 2 && echo 'Comando 1 terminado'"},
                {"bash", "-c", "sleep 3 && echo 'Comando 2 terminado'"},
                {"bash", "-c", "sleep 1 && echo 'Comando 3 terminado'"}
        };

        System.out.println("=== EJECUCIÓN SECUENCIAL ===");
        long tiempoSecuencial = ejecutarSecuencial(comandos);
        System.out.println("Tiempo total secuencial: " + tiempoSecuencial + " ms\n");

        System.out.println("=== EJECUCIÓN PARALELA ===");
        long tiempoParalelo = ejecutarParalelo(comandos);
        System.out.println("Tiempo total paralelo: " + tiempoParalelo + " ms\n");

        guardarResultados(tiempoSecuencial, tiempoParalelo);
    }

    // --- Ejecución SECUENCIAL ---
    private static long ejecutarSecuencial(String[][] comandos) {
        long inicio = System.currentTimeMillis();

        for (String[] comando : comandos) {
            ejecutarProceso(comando);
        }

        long fin = System.currentTimeMillis();
        return fin - inicio;
    }

    // --- Ejecución PARALELA ---
    private static long ejecutarParalelo(String[][] comandos) {
        long inicio = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(comandos.length);

        for (String[] comando : comandos) {
            executor.submit(() -> ejecutarProceso(comando));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long fin = System.currentTimeMillis();
        return fin - inicio;
    }

    // --- Ejecutar un proceso shell ---
    private static void ejecutarProceso(String[] comando) {
        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar comando: " + String.join(" ", comando));
            e.printStackTrace();
        }
    }

    // --- Guardar resultados en logs/resultados_multiproceso.txt ---
    private static void guardarResultados(long tiempoSecuencial, long tiempoParalelo) {
        File logDir = new File("logs");
        if (!logDir.exists()) logDir.mkdirs();

        File archivo = new File(logDir, "resultados_multiproceso.txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println("----- RESULTADOS DE EJECUCIÓN -----");
            pw.println("Tiempo secuencial: " + tiempoSecuencial + " ms");
            pw.println("Tiempo paralelo: " + tiempoParalelo + " ms");
            pw.println("Diferencia: " + (tiempoSecuencial - tiempoParalelo) + " ms");
            pw.println("-----------------------------------\n");
            System.out.println("Resultados guardados en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
