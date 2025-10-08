import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

// Programa que ejecuta LectorCadena
public class EjecutorProceso {

    public static void Main(String[] args) {

        try {
            // Crear el proceso
            ProcessBuilder pb = new ProcessBuilder("java", "LectorCadena");
            Process p = pb.start();

            // Para escribir al proceso
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            // Para leer la salida del proceso
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // Enviar texto al proceso
            String texto = "Hola mundo desde el proceso padre*";
            bw.write(texto);
            bw.close();

            // Leer y mostrar la salida
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que termine
            int resultado = p.waitFor();
            System.out.println("\nProceso terminado con código: " + resultado);

            br.close();

        } catch (Exception e) {
            System.out.println("Error al ejecutar el proceso: " + e.getMessage());
        }
    }
}