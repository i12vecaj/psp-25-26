import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParteCuatroEncadenarProcesos {
    public static void main(String[] args){

        try {
            // Primer proceso: listar directorio en lista.txt
            ProcessBuilder pb4a = new ProcessBuilder("cmd", "/c", "dir");
            /*
            new ProcessBuilder("cmd", "/c", "dir"): Crea un proceso que ejecuta el comando dir en Windows.
            "cmd" abre el intérprete de comandos.
            "/c" le dice a cmd que ejecute el comando y termine.
            "dir" lista los archivos del directorio actual.
             */

            
            File salidaLista = new File("lista.txt");
            pb4a.redirectOutput(salidaLista);
            Process p4a = pb4a.start();

            // Esperamos a que termine y guardamos el exit code
            int exitCode = p4a.waitFor();

            // Segundo proceso: mostrar contenido si no hubo error
            if (exitCode == 0) {
                ProcessBuilder pb4b = new ProcessBuilder("cmd", "/c", "type", "lista.txt");
                Process p4b = pb4b.start();
                mostrarSalida(p4b);
            } else {
                System.out.println("El primer proceso falló con exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Metodo para mostrar la salida de un proceso
    private static void mostrarSalida(Process p) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(p.getInputStream()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        }

    }
}
