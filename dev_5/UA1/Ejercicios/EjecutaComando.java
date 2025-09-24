import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComando {
    public static void main(String[] args) {
        try {
 
            ProcessBuilder pb = new ProcessBuilder("ping", "google.com");

            Process proceso = pb.start();
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(proceso.getInputStream())
            );
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            
            int exitCode = proceso.waitFor();
            System.out.println("El proceso terminó con código: " + exitCode);
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
