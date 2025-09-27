import java.io.*;

public class ProcessSimulator {
    public static void main(String[] args) throws IOException {
        // Creamos la carpeta logs si no existe
        File carpetaLogs = new File("logs");
        if (!carpetaLogs.exists()) {
            carpetaLogs.mkdirs(); // mkdirs() crea carpetas si no existen
        }

        // Archivo de salida donde guardaremos los resultados
        File archivoLog = new File("logs/resultados_multiproceso.txt");
        PrintWriter escritor = new PrintWriter(new FileWriter(archivoLog, false)); // false = sobrescribir

        // 1. Ejecución en paralelo
        System.out.println("Iniciando ejecución en paralelo...");
        escritor.println("=== EJECUCIÓN EN PARALELO ===");

        // Guardamos el tiempo de inicio
        long inicioParalelo = System.currentTimeMillis();

        // Creamos los 3 procesos
        ProcessBuilder pb1 = new ProcessBuilder("java", "Worker1");
        ProcessBuilder pb2 = new ProcessBuilder("java", "Worker2");
        ProcessBuilder pb3 = new ProcessBuilder("java", "Worker3");

        // Redirigimos errores a la salida estándar por si acaso
        pb1.redirectErrorStream(true);
        pb2.redirectErrorStream(true);
        pb3.redirectErrorStream(true);

        // Iniciamos los procesos
        Process p1 = pb1.start();
        Process p2 = pb2.start();
        Process p3 = pb3.start();

        try {
            // Esperamos a que todos terminen
            p1.waitFor();
            p2.waitFor();
            p3.waitFor();
        } catch (InterruptedException e) {
            System.out.println("Error esperando a los procesos paralelos.");
        }

        long finParalelo = System.currentTimeMillis(); // Tiempo final
        long tiempoParalelo = finParalelo - inicioParalelo;

        escritor.println("Tiempo total en paralelo: " + tiempoParalelo + " ms\n");

        // 2. Ejecución secuencial
        System.out.println("Iniciando ejecución secuencial...");
        escritor.println("=== EJECUCIÓN SECUENCIAL ===");

        long inicioSecuencial = System.currentTimeMillis();

        try {
            // Ejecutamos los procesos uno detrás de otro
            new ProcessBuilder("java", "Worker1").start().waitFor();
            new ProcessBuilder("java", "Worker2").start().waitFor();
            new ProcessBuilder("java", "Worker3").start().waitFor();
        } catch (InterruptedException e) {
            System.out.println("Error esperando a los procesos secuenciales.");
        }

        long finSecuencial = System.currentTimeMillis();
        long tiempoSecuencial = finSecuencial - inicioSecuencial;

        escritor.println("Tiempo total en secuencial: " + tiempoSecuencial + " ms\n");

        // 3. Comparación
        long diferencia = tiempoSecuencial - tiempoParalelo;
        escritor.println("Diferencia de tiempo (secuencial - paralelo): " + diferencia + " ms");

        // Cerramos el archivo
        escritor.close();

        System.out.println("Simulación finalizada. Resultados guardados en logs/resultados_multiproceso.txt");
    }
}
