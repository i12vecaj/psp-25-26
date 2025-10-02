package multiproceso.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessSimulator {
    public static void main(String[] args) {
        String[] clases = {"multiproceso.HolaMundo", "multiproceso.Adios", "multiproceso.Buenas"};
        //cada clase es un proceso

        try {
            long inicioSecuencial = System.currentTimeMillis(); //Tiempo inicial
            long tiempoSecuencial = 0;

            for (String clase : clases) {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", clase);
                Process p = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                //Leo lo que imprime el proceso linea por linea y se muestra por la terminal
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println("Ejecucion secuencial: Salida de : " + clase + "; " + linea);
                }
                //Se utiliza para esperar a que termine el proceso y pase al siguiente
                p.waitFor();
            }

            long finalSecuencial = System.currentTimeMillis();
            tiempoSecuencial = finalSecuencial - inicioSecuencial;

            System.out.println("El tiempo total secuencial es: " + tiempoSecuencial);

            long inicioParalelo = System.currentTimeMillis();

            List<Process> procesos = new ArrayList<>();
            //Para lanzar todos los procesos a la misma vez
            for (String clase : clases) {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", clase);
                Process p = pb.start();
                //cada proceso se añade a la lista
                procesos.add(p);
            }

            //Leo la salida de cada proceso
            for (int i = 0; i < clases.length; i++) {
                Process p = procesos.get(i);
                String clase = clases[i];

                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println("Ejecucion en simultanea: Salida de: " + clase + "; " + linea);
                }
            }

            for (Process p : procesos) {
                p.waitFor(); //Esperar a que terminen los procesos
            }

            long endParalelo = System.currentTimeMillis();
            long tiempoParalelo = endParalelo - inicioParalelo;

            System.out.println("Tiempo total paralelo: " + tiempoParalelo);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("logs/resultados_multiproceso.txt"))) {
                bw.write("Tiempo total secuencial: " + tiempoSecuencial + " ms");
                bw.newLine(); // salto de línea
                bw.write("Tiempo total paralelo: " + tiempoParalelo + " ms");
                bw.newLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace(); // Capturamos cualquier error de los procesos

        }

    }
}
