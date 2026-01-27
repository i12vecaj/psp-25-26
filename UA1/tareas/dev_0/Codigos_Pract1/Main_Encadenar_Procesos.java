import java.io.File;
import java.io.IOException;

public class Main_Encadenar_Procesos {
    public static void main(String[] args) {
        try {
            File salida = new File("listado.txt");

            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb1;

            if (os.contains("win")) {
                pb1 = new ProcessBuilder("cmd", "/c", "dir");
            } else {
                pb1 = new ProcessBuilder("ls");
            }

            pb1.redirectOutput(salida);
            pb1.redirectError(salida);

            Process proceso1 = pb1.start();
            int exitCode1 = proceso1.waitFor();

            System.out.println("Primer comando finalizado con código: " + exitCode1);

            if (exitCode1 == 0) {
                ProcessBuilder pb2;

                if (os.contains("win")) {
                    pb2 = new ProcessBuilder("cmd", "/c", "type listado.txt");
                } else {
                    pb2 = new ProcessBuilder("cat", "listado.txt");
                }

                pb2.inheritIO();
                Process proceso2 = pb2.start();
                int exitCode2 = proceso2.waitFor();

                System.out.println("Segundo comando finalizado con código: " + exitCode2);
            } else {
                System.out.println("El primer comando falló. No se ejecuta el segundo.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
