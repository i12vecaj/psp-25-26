package Parte3;
import java.io.*;
public class EjecutaComando3 {
    public static void main(String[] args) throws IOException {
        Process p = new ProcessBuilder("CMD", "/C", "ping google.com").start();
        try (
                InputStream is = p.getInputStream();
                FileWriter fw = new FileWriter("Salida.txt");
                PrintWriter pw = new PrintWriter(fw)
        ) {
            int c;
            while ((c = is.read()) != -1) {
                char ch = (char) c;
                System.out.print(ch);
                pw.print(ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}