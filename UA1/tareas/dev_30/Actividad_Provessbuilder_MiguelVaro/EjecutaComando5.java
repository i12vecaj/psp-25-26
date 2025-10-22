package Parte5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
public class EjecutaComando5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Ejecutor de Comandos del Sistema ---");
        System.out.println("ADVERTENCIA: Sea cuidadoso con el comando que ingresa.");
        System.out.print("Introduce un comando (ej: ls -l, cmd /c dir, echo Hola): ");

        String entradaUsuario = scanner.nextLine().trim();
        scanner.close();

        if (entradaUsuario.isEmpty()) {
            System.out.println("No se introdujo ningún comando.");
            return;
        }

        String[] partesComando = entradaUsuario.split("\\s+");

        ProcessBuilder pb = new ProcessBuilder(Arrays.asList(partesComando));

        pb.redirectErrorStream(true);

        try {
            System.out.println("\n--- Ejecutando: " + entradaUsuario + " ---");
            Process proceso = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int exitCode = proceso.waitFor();
            System.out.println("\n--- Comando finalizado con código de salida: " + exitCode + " ---");

        } catch (IOException e) {
            System.err.println("\nERROR: No se pudo ejecutar el comando. Asegúrese de que el comando base sea válido.");
            System.err.println("Detalle del error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}