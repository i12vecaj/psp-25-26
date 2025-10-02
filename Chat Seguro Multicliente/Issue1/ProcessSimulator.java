package Sprint1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class ProcessSimulator {
    public static void main(String[] args) throws IOException {
        String fichero = "resultados_multiproceso.txt";
        Process p = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com").start();
        Process p2 = new ProcessBuilder("cmd.exe", "/c", "nslookup 8.8.8.8").start();
        Process p3 = new ProcessBuilder("cmd.exe", "/c", "ipconfig").start();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {

            InputStream is = p.getInputStream();
            InputStream is2 = p2.getInputStream();
            InputStream is3 = p3.getInputStream();

            int c;

            // ---- Primer proceso ----
            while ((c = is.read()) != -1) {
                bw.write(c);
            }
            is.close();
            bw.newLine();

            // ---- Segundo proceso ----
            while ((c = is2.read()) != -1) {
                bw.write(c);
            }
            is2.close();
            bw.newLine();

            // ---- Tercer proceso ----
            while ((c = is3.read()) != -1) {
                bw.write(c);
            }
            is3.close();
            bw.newLine();

            // esperar a que los procesos acaben
            try {
                p.waitFor();
                p2.waitFor();
                p3.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
