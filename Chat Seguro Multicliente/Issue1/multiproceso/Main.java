import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN MULTIPROCESO ===");

        // Ejecutar en paralelo
        long inicioParalelo = System.currentTimeMillis();
        ejecutarEnParalelo();
        long finParalelo = System.currentTimeMillis();

        // Ejecutar secuencial
        long inicioSecuencial = System.currentTimeMillis();
        ejecutarSecuencial();
        long finSecuencial = System.currentTimeMillis();

        // Calcular tiempos
        long tiempoParalelo = finParalelo - inicioParalelo;
        long tiempoSecuencial = finSecuencial - inicioSecuencial;

        // Guardar resultados
        guardarResultados(tiempoParalelo, tiempoSecuencial);

        System.out.println("Tiempo paralelo: " + tiempoParalelo + " ms");
        System.out.println("Tiempo secuencial: " + tiempoSecuencial + " ms");
    }

    private static void ejecutarEnParalelo() {
        List<Process> procesos = new ArrayList<>();

        try {
            // Crear procesos para cada script
            ProcessBuilder pb1 = new ProcessBuilder("java", "Script1");
            ProcessBuilder pb2 = new ProcessBuilder("java", "Script2");
            ProcessBuilder pb3 = new ProcessBuilder("java", "Script3");

            // Redirigir salida estándar
            pb1.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb2.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb3.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            System.out.println("Iniciando ejecución paralela...");

            // Iniciar todos los procesos
            procesos.add(pb1.start());
            procesos.add(pb2.start());
            procesos.add(pb3.start());

            // Esperar a que todos terminen
            for (Process proceso : procesos) {
                proceso.waitFor();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void ejecutarSecuencial() {
        try {
            System.out.println("Iniciando ejecución secuencial...");

            // Ejecutar uno tras otro
            Process p1 = new ProcessBuilder("java", "Script1").start();
            p1.waitFor();

            Process p2 = new ProcessBuilder("java", "Script2").start();
            p2.waitFor();

            Process p3 = new ProcessBuilder("java", "Script3").start();
            p3.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void guardarResultados(long tiempoParalelo, long tiempoSecuencial) {
        // Crear directorio logs si no existe
        File directorioLogs = new File("logs");
        if (!directorioLogs.exists()) {
            directorioLogs.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("logs/resultados_multiproceso.txt"))) {
            writer.println("=== RESULTADOS SIMULACIÓN MULTIPROCESO ===");
            writer.println("Fecha: " + new java.util.Date());
            writer.println("Tiempo ejecución paralela: " + tiempoParalelo + " ms");
            writer.println("Tiempo ejecución secuencial: " + tiempoSecuencial + " ms");
            writer.println("Diferencia: " + (tiempoSecuencial - tiempoParalelo) + " ms");
            writer.println("Mejora: " + String.format("%.2f", ((double)tiempoSecuencial/tiempoParalelo)) + "x más rápido");

            writer.println("\n=== EXPLICACIÓN TÉCNICA ===");
            writer.println("Proceso: Programa en ejecución con su propio espacio de memoria");
            writer.println("Hilo: Unidad de ejecución dentro de un proceso que comparte memoria");
            writer.println("ProcessBuilder: Crea procesos del SO, no hilos de Java");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}