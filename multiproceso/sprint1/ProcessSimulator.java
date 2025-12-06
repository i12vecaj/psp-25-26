package sprint1;

import java.io.*;

public class ProcessSimulator {
    public static void main(String[] args) throws IOException {
        // Crear carpeta "logs" si aún no existe
        File carpetaLogs = new File("logs");
        if (!carpetaLogs.exists()) {
            carpetaLogs.mkdirs(); // mkdirs() crea todas las carpetas necesarias
        }

        // Archivo donde se guardarán los resultados
        File archivoLog = new File("logs/resultados_multiproceso.txt");
        PrintWriter escritor = new PrintWriter(new FileWriter(archivoLog, false)); // false = sobrescribe

        // 1. Ejecución en paralelo
        System.out.println("Iniciando ejecución en paralelo...");
        escritor.println("=== EJECUCIÓN EN PARALELO ===");

        // Guardar tiempo inicial
        long inicioParalelo = System.currentTimeMillis();

        // Definir 3 procesos distintos
        ProcessBuilder pb1 = new ProcessBuilder("java", "Worker1");
        ProcessBuilder pb2 = new ProcessBuilder("java", "Worker2");
        ProcessBuilder pb3 = new ProcessBuilder("java", "Worker3");

        // Unir salida de error con salida normal
        pb1.redirectErrorStream(true);
        pb2.redirectErrorStream(true);
        pb3.redirectErrorStream(true);

        // Arrancar los procesos
        Process p1 = pb1.start();
        Process p2 = pb2.start();
        Process p3 = pb3.start();

        try {
            // Esperar a que todos acaben
            p1.waitFor();
            p2.waitFor();
            p3.waitFor();
        } catch (InterruptedException e) {
            System.out.println("Error esperando procesos paralelos.");
        }

        long finParalelo = System.currentTimeMillis(); // Tiempo final
        long tiempoParalelo = finParalelo - inicioParalelo;

        escritor.println("Tiempo total en paralelo: " + tiempoParalelo + " ms\n");

        // 2. Ejecución secuencial
        System.out.println("Iniciando ejecución secuencial...");
        escritor.println("=== EJECUCIÓN SECUENCIAL ===");

        long inicioSecuencial = System.currentTimeMillis();

        try {
            // Ejecutar procesos uno tras otro
            new ProcessBuilder("java", "Worker1").start().waitFor();
            new ProcessBuilder("java", "Worker2").start().waitFor();
            new ProcessBuilder("java", "Worker3").start().waitFor();
        } catch (InterruptedException e) {
            System.out.println("Error esperando procesos secuenciales.");
        }

        long finSecuencial = System.currentTimeMillis();
        long tiempoSecuencial = finSecuencial - inicioSecuencial;

        escritor.println("Tiempo total en secuencial: " + tiempoSecuencial + " ms\n");

        // 3. Comparar tiempos
        long diferencia = tiempoSecuencial - tiempoParalelo;
        escritor.println("Diferencia de tiempo (secuencial - paralelo): " + diferencia + " ms");

        // Cerrar archivo
        escritor.close();

        System.out.println("Simulación finalizada. Resultados en logs/resultados_multiproceso.txt");
    }
}
