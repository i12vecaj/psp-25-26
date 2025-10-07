import java.io.*;

public class RedirigirSalida {
    public static void main(String[] args) {
        try {
            ProcessBuilder proceso = new ProcessBuilder("cmd", "/c", "ping -n 3 google.com");
            File archivoSalida = new File("resultado.txt");

            proceso.redirectOutput(archivoSalida);
            proceso.redirectErrorStream(true);

            Process ejecucion = proceso.start();
            ejecucion.waitFor();

            System.out.println("Comando completado. Verifique el archivo resultado.txt.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
