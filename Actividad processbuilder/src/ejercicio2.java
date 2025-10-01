import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ejercicio2 {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("ping", "ankllfbejhgieh.com");
        pb.redirectErrorStream(true);  //
        Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
    }
}