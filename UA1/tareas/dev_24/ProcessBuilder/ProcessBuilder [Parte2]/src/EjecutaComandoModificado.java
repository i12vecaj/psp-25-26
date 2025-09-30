import java.io.*;

public class EjecutaComandoModificado {
    public static void main(String[] args) {
        try {

            ProcessBuilder pb = new ProcessBuilder("ping", "-c", "4", "direcccionInexistente.test");
            pb.redirectErrorStream(true);

            Process p = pb.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

            //para escribir en consola
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
