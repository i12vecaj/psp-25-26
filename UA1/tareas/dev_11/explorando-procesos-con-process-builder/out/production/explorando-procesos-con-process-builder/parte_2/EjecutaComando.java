package parte_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComando {
    public static void main(String[] args) throws IOException {
        // 1. Definir el comando a una dirección que no existe
        ProcessBuilder pb = new ProcessBuilder("ping", "-c", "2", "direcccionInexistente.test");

        // 2. Redirigir errores a la salida estándar
        pb.redirectErrorStream(true);
        // Esto une stderr (la salida de errores) con stdout (la salida normal). Así se pueden leer ambos usando solo p.getInputStream() sin necesidad de capturar dos flujos distintos

        // 3. Iniciar el proceso
        Process p = pb.start();

        // 4. Leer la salida del proceso
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea); // Aquí se mostrarán los mensajes de error también
        }

        // 5. Esperar a que termine y mostrar el código de salida
        try {
            int exitCode = p.waitFor();
            System.out.println("Proceso finalizado con código: " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
