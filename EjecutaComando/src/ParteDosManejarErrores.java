import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParteDosManejarErrores {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping direcccionInexistente.test");

        // Redirigir error al flujo estándar
        pb.redirectErrorStream(true);

        Process p = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }
    }
}
