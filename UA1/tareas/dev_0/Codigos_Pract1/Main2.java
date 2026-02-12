import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main2 {
    public static void main(String[] args) {
        try {
            // Comando ping en Windows (-n indica el número de repeticiones)
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 direcccionInexistente.test");

            // Redirigir errores al mismo flujo que la salida estándar
            pb.redirectErrorStream(true);

            // Iniciar el proceso
            Process p = pb.start();

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Esperar a que finalice y mostrar el código de salida
            int exitCode = p.waitFor();
            System.out.println("\nEl comando terminó con código: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
