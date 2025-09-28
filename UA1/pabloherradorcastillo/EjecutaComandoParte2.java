package pabloherradorcastillo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComandoParte2 {
    public static void main(String[] args) {
        // Comando de ping a una dirección inexistente (Windows usa -n)
        ProcessBuilder pb = new ProcessBuilder(
                "ping", "-n", "2", "direccionInexistente.test");

        // Redirigir la salida de error al flujo estándar
        pb.redirectErrorStream(true);

        try {
            // Iniciar el proceso
            Process proceso = pb.start();

            // Capturar tanto la salida estándar como los errores
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));

            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que el proceso termine y mostrar el código de salida
            int exitCode = proceso.waitFor();
            System.out.println("\nProceso terminado con código: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
