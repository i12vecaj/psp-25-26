import java.io.*;

public class EjecutaComando {
    public static void main(String[] args) {
        try {
            Process p = new ProcessBuilder("ping", "-c", "4", "google.com").start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
