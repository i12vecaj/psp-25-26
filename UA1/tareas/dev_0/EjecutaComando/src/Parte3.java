import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

//Parte 3 - Redirigir salida a un fichero
public class Parte3 {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder( "CMD", "/C", "ping google.com");
        File salida = new File("salida.txt");

        pb.redirectOutput(salida);

        Process p = pb.start();

        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
            System.out.println("La salida se ha guardado con éxito en salida.txt");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}