package parte_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComando {
    public static void main(String[] args) throws IOException {
        // 1. Definir el comando
        // El argumento "-c 2" significa que solo mande 2 paquetes (esto funciona en Linux/Mac)
        // En Windows sería: new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com");
        ProcessBuilder pb = new ProcessBuilder("ping", "-c", "2", "google.com");

        // 2. Redirigir errores a la salida estándar
        pb.redirectErrorStream(true);

        // 3. Iniciar el proceso
        Process p = pb.start();

        // 4. Leer la salida del proceso
        // El proceso (ping) va a imprimir texto en pantalla, por eso usamos un BufferedReader
        // (https://codegym.cc/es/groups/posts/es.150.bufferedreader-y-bufferedwriter)
        // new InputStreamReader(p.getInputStream()) -> convierte la salida del proceso en "texto legible"
        // Para entender getInputStream() y getOutputStream() : (https://stackoverflow.com/questions/22563986/understanding-getinputstream-and-getoutputstream)
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        // Leemos línea a línea lo que el comando va imprimiendo
        String linea;
        while ((linea = reader.readLine()) != null) {
            // Cada línea la mostramos en nuestra consola
            System.out.println(linea);
        }

        // 5. Esperar a que termine y mostrar el código de salida
        // El código de salida es un número que indica si el proceso fue bien (0) o hubo error (distinto de 0)
        try {
            int exitCode = p.waitFor(); // Espera hasta que el proceso ping termine
            // qué es waitFor(): (https://docs.oracle.com/javase/8/docs/api/java/lang/Process.html#waitFor--)
            System.out.println("Proceso finalizado con código: " + exitCode);
        } catch (InterruptedException e) {
            // Si algo interrumpe la espera, mostramos el error
            e.printStackTrace();
        }

    }
}
