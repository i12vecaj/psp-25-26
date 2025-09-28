import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class EjecutaComandoFichero {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "PING google.com");
        Process p = pb.start();
        FileWriter fichero = new FileWriter("salida.txt");

        try {
            InputStream is = p.getInputStream();
            int i;
            while ((i = is.read()) != -1){
                char data = ((char) i);
                System.out.print((char) i);
                fichero.write(data);}
                is.close();

            fichero.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}