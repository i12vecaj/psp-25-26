import java.io.*;

public class EncadenaProcesos {
    public static void main(String[] args) throws Exception {
        Process p1 = new ProcessBuilder("ls").redirectOutput(new File("listado.txt")).redirectErrorStream(true).start();
        if (p1.waitFor() == 0) {
            BufferedReader r = new BufferedReader(new InputStreamReader(new ProcessBuilder("cat", "listado.txt").start().getInputStream()));
            String l; while ((l = r.readLine()) != null) System.out.println(l);
        }
    }
}
