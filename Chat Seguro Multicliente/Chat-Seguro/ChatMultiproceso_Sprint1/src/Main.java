import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] programas = {"multiproceso.HolaMundo", "multiproceso.Adios", "multiproceso.Buenas"};

        try {
            long inicioSecuencial = System.currentTimeMillis();
            long duracionSecuencial;

            // Ejecución secuencial
            for (String prog : programas) {
                ProcessBuilder secuencial = new ProcessBuilder("java", "-cp", "bin", prog);
                Process proceso = secuencial.start();

                try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                    String salida;
                    while ((salida = lector.readLine()) != null) {
                        System.out.println("[Secuencial] " + prog + " => " + salida);
                    }
                }

                proceso.waitFor();
            }

            duracionSecuencial = System.currentTimeMillis() - inicioSecuencial;
            System.out.println("Duración total secuencial: " + duracionSecuencial + " ms");

            // Ejecución paralela
            long inicioParalelo = System.currentTimeMillis();
            List<Process> listaProcesos = new ArrayList<>();

            for (String prog : programas) {
                ProcessBuilder paralelo = new ProcessBuilder("java", "-cp", "bin", prog);
                listaProcesos.add(paralelo.start());
            }

            for (int i = 0; i < listaProcesos.size(); i++) {
                try (BufferedReader lector = new BufferedReader(new InputStreamReader(listaProcesos.get(i).getInputStream()))) {
                    String salida;
                    while ((salida = lector.readLine()) != null) {
                        System.out.println("[Paralelo] " + programas[i] + " => " + salida);
                    }
                }
            }

            for (Process pr : listaProcesos) {
                pr.waitFor();
            }

            long duracionParalelo = System.currentTimeMillis() - inicioParalelo;
            System.out.println("Duración total paralela: " + duracionParalelo + " ms");

            // Guardar resultados
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter("logs/tiempos.txt"))) {
                escritor.write("Secuencial: " + duracionSecuencial + " ms");
                escritor.newLine();
                escritor.write("Paralelo: " + duracionParalelo + " ms");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
