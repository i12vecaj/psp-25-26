import java.io.File;

public class ejercicio3 {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("ping", "google.com");
        pb.redirectOutput(new File("salida.txt"));  // salida al fichero
        pb.start();
    }
}