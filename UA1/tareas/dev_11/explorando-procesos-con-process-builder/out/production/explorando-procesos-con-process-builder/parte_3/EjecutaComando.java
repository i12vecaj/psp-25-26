package parte_3;

import java.io.File;
import java.io.IOException;

public class EjecutaComando {
    public static void main(String[] args) throws IOException {
        // 1. Definir el comando (aquí puedes usar una dirección válida o inválida)
        ProcessBuilder pb = new ProcessBuilder("ping", "-c", "2", "google.com");

        // 2. Redirigir errores a la salida estándar
        pb.redirectErrorStream(true);

        // 3. Redirigir la salida a un archivo
        pb.redirectOutput(new File("salida.txt"));

        // 4. Iniciar el proceso
        Process p = pb.start();

        // 5. Esperar a que termine
        try {
            int exitCode = p.waitFor();
            System.out.println("Proceso finalizado con código: " + exitCode);
            System.out.println("Consulta el archivo salida.txt para ver el resultado.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
