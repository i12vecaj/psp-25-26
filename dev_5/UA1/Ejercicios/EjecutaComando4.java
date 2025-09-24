import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComando4 {
    public static void main(String[] args) {
        try {
        	File Lista = new File("Lista.txt");
            ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "dir");

            pb1.redirectOutput(Lista);
            
            pb1.redirectErrorStream(true);
            
            Process proceso1 = pb1.start();
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(proceso1.getInputStream())
            );
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            
            int exitCode1 = proceso1.waitFor();
            System.out.println("El proceso terminó con código: " + exitCode1 +"\n Revise el archivo salida.txt");
            if (exitCode1 == 0) {
                ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "type", "Lista.txt");
                pb2.inheritIO(); 
                Process p2 = pb2.start();
                int exitCode2 = p2.waitFor();
                System.out.println("Segundo comando terminó con código: " + exitCode2);
            } else {
                System.out.println("El primer comando falló, no se ejecuta el segundo.");
            }

            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
