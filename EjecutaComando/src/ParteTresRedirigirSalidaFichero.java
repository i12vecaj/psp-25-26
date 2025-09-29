import java.io.File;
import java.io.IOException;

public class ParteTresRedirigirSalidaFichero {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 3 google.com");

        //LLevar la salida al fichero
        File salida = new File("salida.txt");
        pb.redirectOutput(salida);

        //Redirigir errores al mismo archivo
        pb.redirectErrorStream(true);

        Process p = pb.start();

        System.out.println("El comando ya se ha realizado, revisar el archivo salida.txt");
    }
}
