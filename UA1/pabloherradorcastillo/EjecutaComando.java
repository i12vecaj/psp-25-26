package pabloherradorcastillo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComando {
    public static void main(String[] args) {
        // En Windows se usa -n para indicar el número de pings
        ProcessBuilder pb = new ProcessBuilder("ping", "-n", "2", "google.com");

        try {
            // Iniciar el proceso
            Process proceso = pb.start();

            // Capturar y mostrar la salida del comando
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
