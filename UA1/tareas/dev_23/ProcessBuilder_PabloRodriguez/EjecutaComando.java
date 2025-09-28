import java.io.*;
public class EjecutaComando {
    public static void main(String[] args) throws IOException  {
        Process p = new ProcessBuilder("CMD", "/C", "PING google.com").start();
        try {
            InputStream is = p.getInputStream();
            int i;
            while ((i = is.read()) != -1)
                System.out.print((char) i);
            is.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

}}
