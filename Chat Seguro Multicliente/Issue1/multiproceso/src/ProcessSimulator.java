import java.io.*;

public class ProcessSimulator {
    public static void main(String[] args) {
        try {
            // Creamos la carpeta logs si no existe
            File carpetaLogs = new File("logs");
            if (!carpetaLogs.exists()) {
                carpetaLogs.mkdirs();
            }

            // Archivo de salida donde guardaremos los resultados
            File archivoLog = new File("logs/resultados_multiproceso.txt");
            PrintWriter logWriter = new PrintWriter(new FileWriter(archivoLog, false));

            // Obtener el classpath del sistema
            String classpath = System.getProperty("java.class.path");

            // 1. Ejecución en paralelo
            System.out.println("Iniciando ejecución en paralelo...");
            logWriter.println("=== EJECUCIÓN EN PARALELO ===");

            long inicioParalelo = System.currentTimeMillis();

            try {
                // Creamos los 3 procesos con classpath del sistema
                ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", classpath, "Trabajador1");
                ProcessBuilder pb2 = new ProcessBuilder("java", "-cp", classpath, "Trabajador2");
                ProcessBuilder pb3 = new ProcessBuilder("java", "-cp", classpath, "Trabajador3");

                // Redirigimos errores a la salida estándar
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
                    System.out.println("Error esperando a los procesos paralelos: " + e.getMessage());
                    logWriter.println("Error esperando a los procesos paralelos: " + e.getMessage());
                }

            } catch (IOException e) {
                System.out.println("Error creando procesos paralelos: " + e.getMessage());
                logWriter.println("Error creando procesos paralelos: " + e.getMessage());
            }

            long finParalelo = System.currentTimeMillis();
            long tiempoParalelo = finParalelo - inicioParalelo;
            System.out.println("Tiempo paralelo: " + tiempoParalelo + " ms");
            logWriter.println("Tiempo total en paralelo: " + tiempoParalelo + " ms");

            // 2. Ejecución secuencial
            System.out.println("Iniciando ejecución secuencial...");
            logWriter.println("=== EJECUCIÓN SECUENCIAL ===");

            long inicioSecuencial = System.currentTimeMillis();

            try {
                // Ejecutamos los procesos uno detrás de otro
                try {
                    new ProcessBuilder("java", "-cp", classpath, "Trabajador1").start().waitFor();
                } catch (IOException e) {
                    System.out.println("Error ejecutando Trabajador1: " + e.getMessage());
                    logWriter.println("Error ejecutando Trabajador1: " + e.getMessage());
                }

                try {
                    new ProcessBuilder("java", "-cp", classpath, "Trabajador2").start().waitFor();
                } catch (IOException e) {
                    System.out.println("Error ejecutando Trabajador2: " + e.getMessage());
                    logWriter.println("Error ejecutando Trabajador2: " + e.getMessage());
                }

                try {
                    new ProcessBuilder("java", "-cp", classpath, "Trabajador3").start().waitFor();
                } catch (IOException e) {
                    System.out.println("Error ejecutando Trabajador3: " + e.getMessage());
                    logWriter.println("Error ejecutando Trabajador3: " + e.getMessage());
                }

            } catch (InterruptedException e) {
                System.out.println("Error esperando a los procesos secuenciales: " + e.getMessage());
                logWriter.println("Error esperando a los procesos secuenciales: " + e.getMessage());
            }

            long finSecuencial = System.currentTimeMillis();
            long tiempoSecuencial = finSecuencial - inicioSecuencial;
            System.out.println("Tiempo secuencial: " + tiempoSecuencial + " ms");
            logWriter.println("Tiempo total en secuencial: " + tiempoSecuencial + " ms");

            // 3. Comparación
            long diferencia = tiempoSecuencial - tiempoParalelo;
            double incremento = (double) tiempoSecuencial / tiempoParalelo;
            double mejora = ((double)(tiempoSecuencial - tiempoParalelo) / tiempoSecuencial) * 100;

            System.out.println("=====================");
            System.out.println("Comparación");
            System.out.println("Tiempo paralelo: "+ tiempoParalelo + " ms");
            System.out.println("Tiempo secuencial: "+ tiempoSecuencial + " ms");
            System.out.println("Diferencia: " + diferencia + " ms");
            System.out.println("Incremento: " + String.format("%.2f", incremento) + "x");
            System.out.println("Mejora: " + String.format("%.1f", mejora) + "%");

            logWriter.println("=====================");
            logWriter.println("Comparación");
            logWriter.println("Tiempo paralelo: "+ tiempoParalelo + " ms");
            logWriter.println("Tiempo secuencial: "+ tiempoSecuencial + " ms");
            logWriter.println("Diferencia: " + diferencia + " ms");
            logWriter.println("Incremento: " + String.format("%.2f", incremento) + "x");
            logWriter.println("Mejora: " + String.format("%.1f", mejora) + "%");

            // Cerramos el archivo
            logWriter.close();
            System.out.println("Resultados guardados en logs/resultados_multiproceso.txt");

        } catch (IOException e) {
            System.out.println("Error al crear el archivo de log: " + e.getMessage());
        }
    }
}