import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComando3 {
    public static void main(String[] args) {
        try {
 
            ProcessBuilder pb = new ProcessBuilder("ping", "direcccionInexistente.test");
            
            File archivoSalida = new File("salida.txt");
            pb.redirectOutput(archivoSalida);

            pb.redirectErrorStream(true);
            
            Process proceso = pb.start();
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(proceso.getInputStream())
            );
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            
            int exitCode = proceso.waitFor();
            System.out.println("El proceso terminó con código: " + exitCode +"\n Revise el archivo salida.txt");

            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
