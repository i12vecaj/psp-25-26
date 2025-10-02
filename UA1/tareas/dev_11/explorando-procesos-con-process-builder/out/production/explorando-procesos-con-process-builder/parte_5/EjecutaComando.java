package parte_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class EjecutaComando {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce un comando del sistema: ");
        String comandoUsuario = scanner.nextLine();

        // Separamos el comando en partes (por espacios)
        String[] partesComando = comandoUsuario.split("\\s+");

        ProcessBuilder pb = new ProcessBuilder(partesComando);
        pb.redirectErrorStream(true); // Redirige errores a la salida estándar

        try {
            Process proceso = pb.start();

            // Leemos la salida del comando
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            int exitCode = proceso.waitFor();
            System.out.println("Proceso finalizado con código: " + exitCode);

        } catch (IOException e) {
            System.out.println("Error al ejecutar el comando: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
