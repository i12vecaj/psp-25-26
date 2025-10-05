import java.io.*;
import java.util.*;

public class ProcessSimulator {
    public static void main(String[] args) {
        try {
            String[] comandos = {
                "java -cp . Script1.java",
                "java -cp . Script2.java",
                "java -cp . Script3.java"
            };

            File logDir = new File("logs");
            if (!logDir.exists()) logDir.mkdirs();

            File logFile = new File("logs/resultados_multiproceso.txt");
            PrintWriter logWriter = new PrintWriter(new FileWriter(logFile, true));

            logWriter.println("===== RESULTADOS DE EJECUCIÓN =====");
            logWriter.println("Fecha: " + new Date());
            logWriter.println("------------------------------------");

            //  EJECUCIÓN SECUENCIAL
            long inicioSecuencial = System.currentTimeMillis();
            for (String comando : comandos) {
                ProcessBuilder pb = new ProcessBuilder(comando.split(" "));
                pb.inheritIO();
                Process proceso = pb.start();
                proceso.waitFor();
            }
            long finSecuencial = System.currentTimeMillis();
            long tiempoSecuencial = finSecuencial - inicioSecuencial;

            //  EJECUCIÓN PARALELA
            long inicioParalelo = System.currentTimeMillis();
            List<Process> procesos = new ArrayList<>();
            for (String comando : comandos) {
                ProcessBuilder pb = new ProcessBuilder(comando.split(" "));
                pb.inheritIO();
                procesos.add(pb.start());
            }

            for (Process p : procesos) {
                p.waitFor();
            }
            long finParalelo = System.currentTimeMillis();
            long tiempoParalelo = finParalelo - inicioParalelo;

            //RESULTADOS 
            logWriter.println("Ejecución secuencial: " + tiempoSecuencial + " ms");
            logWriter.println("Ejecución paralela:   " + tiempoParalelo + " ms");
            logWriter.println("------------------------------------");
            logWriter.close();

            System.out.println("\n✅ Resultados guardados en logs/resultados_multiproceso.txt");
            System.out.println("Tiempo secuencial: " + tiempoSecuencial + " ms");
            System.out.println("Tiempo paralelo:   " + tiempoParalelo + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

