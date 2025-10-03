import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessSimulator {
    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        // Lista de procesos Java a ejecutar
        List<ProcessBuilder> procesos = new ArrayList<>();
        procesos.add(new ProcessBuilder("java", "-cp", ".", "Proceso1"));
        procesos.add(new ProcessBuilder("java", "-cp", ".", "Proceso2"));
        procesos.add(new ProcessBuilder("java", "-cp", ".", "Proceso3"));

        // Archivo de log
        File logFile = new File("logs/resultados_multiproceso.txt");
        logFile.getParentFile().mkdirs(); // Crear carpeta logs si no existe

        try (BufferedWriter log = new BufferedWriter(new FileWriter(logFile))) {
            List<Process> procesosActivos = new ArrayList<>();

            // Iniciar todos los procesos
            for (ProcessBuilder pb : procesos) {
                pb.redirectErrorStream(true); // errores al mismo flujo que salida
                Process p = pb.start();
                procesosActivos.add(p);
            }

            // Leer salida de cada proceso y guardarla en el log
            for (Process p : procesosActivos) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    log.write(linea);
                    log.newLine();
                }
                p.waitFor(); // espera que termine cada proceso
            }

            long fin = System.currentTimeMillis();
            log.write("Tiempo total de ejecución: " + (fin - inicio) + " ms");
            System.out.println("Resultados guardados en " + logFile.getAbsolutePath());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
