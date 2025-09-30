import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class EjecutaComando3 {
    public static void main(String[] args) throws IOException{

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com");

        pb.redirectOutput(new File("salida.txt"));

        Process p = pb.start();

        try {
        	
            p.waitFor();
            System.out.println("La salida se ha guardado en salida.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
