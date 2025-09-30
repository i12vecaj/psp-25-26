import java.io.*;

public class EjecutaComandoRedirigir {
    public static void main(String[] args) {
        try {
            // Crear ProcessBuilder con el comando ping
            ProcessBuilder pb = new ProcessBuilder("ping", "-c", "4", "google.com");

            // Redirigir la salida a un archivo
            File salida = new File("salida.txt");
            pb.redirectOutput(salida);
            pb.redirectErrorStream(true); // Para que los errores también vayan al archivo

            // Ejecutar el proceso
            Process p = pb.start();
            p.waitFor(); // Espera para que termine el proceso y no se solapen

            System.out.println("La salida se ha guardado en salida.txt");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
