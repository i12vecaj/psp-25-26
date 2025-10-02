package Sprint1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class ProcessSimulatorSecuencial {
    public static void main(String[] args) throws IOException {
        String fichero = "resultados_secuencial.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
            int c;

            // ---- Primer proceso ----
            Process p1 = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com").start();
            InputStream is1 = p1.getInputStream();
            while ((c = is1.read()) != -1) {
                bw.write(c);
            }
            is1.close();
            p1.waitFor();   // esperamos a que termine antes de seguir
            bw.newLine();

            // ---- Segundo proceso ----
            Process p2 = new ProcessBuilder("cmd.exe", "/c", "nslookup 8.8.8.8").start();
            InputStream is2 = p2.getInputStream();
            while ((c = is2.read()) != -1) {
                bw.write(c);
            }
            is2.close();
            p2.waitFor();
            bw.newLine();

            // ---- Tercer proceso ----
            Process p3 = new ProcessBuilder("cmd.exe", "/c", "ipconfig").start();
            InputStream is3 = p3.getInputStream();
            while ((c = is3.read()) != -1) {
                bw.write(c);
            }
            is3.close();
            p3.waitFor();
            bw.newLine();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
