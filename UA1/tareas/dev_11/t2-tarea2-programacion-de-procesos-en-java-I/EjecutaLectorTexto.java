import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaLectorTexto {

    public static void main(String[] args) {
        try {
            ejecutarPrograma("java", "LectorTexto");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al ejecutar el proceso: " + e.getMessage());
        }
    }

    public static void ejecutarPrograma(String comando, String clase) throws IOException, InterruptedException {
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder(comando, "-cp", classpath, clase);
        pb.inheritIO();

        Process proceso = pb.start();

        BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String linea;
        while ((linea = lector.readLine()) != null) {
            System.out.println(linea);
        }

        int exitCode = proceso.waitFor();
        System.out.println("Proceso finalizado con código: " + exitCode);
    }
}
