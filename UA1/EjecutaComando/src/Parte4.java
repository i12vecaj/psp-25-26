import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

//Parte 4 - Encadenar Procesos
public class Parte4 {
    public static void main(String[] args) throws IOException {

        try {
            ProcessBuilder pb1 = new ProcessBuilder("CMD", "/C", "dir");
            File salida = new File("lista.txt");

            pb1.redirectOutput(salida);
            Process p1 = pb1.start();
            int exitVal1 = p1.waitFor();

            System.out.println("El proceso 1 ha finalizado con código: " + exitVal1);

            if (exitVal1 == 0) {
                ProcessBuilder pb2 = new ProcessBuilder("CMD", "/C", "type lista.txt");
                Process p2 = pb2.start();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(p2.getInputStream()))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        System.out.println(linea);
                    }

                }

                int exitVal2 = p2.waitFor();
                System.out.println("El proceso 2 ha finalizado con código: " + exitVal2);
            } else {
                System.out.println("El proceso 1 falló, no se ejecutó el segundo proceso.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}