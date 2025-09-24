import java.io.IOException;
import java.io.InputStream;


public class EjecutaComando {
    public static void main(String[] args) throws IOException {
        Process p = new ProcessBuilder( "CMD", "/C", "ping google.com").start();
        try {
            InputStream is = p.getInputStream();

            int c;
            while ((c = is.read()) != -1)
                System.out.print((char) c);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}