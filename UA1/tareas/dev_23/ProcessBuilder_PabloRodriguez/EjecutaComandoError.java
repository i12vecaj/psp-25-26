import java.io.IOException;
import java.io.InputStream;

public class EjecutaComandoError {
    public static void main(String[] args) throws IOException {
        Process p = new ProcessBuilder("CMD", "/C", "PING direcccionInexistente.test").start();
        try {
            InputStream is = p.getInputStream();
            int i;
            while ((i = is.read()) != -1)
                System.out.print((char) i);
            is.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
