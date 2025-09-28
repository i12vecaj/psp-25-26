package pabloherradorcastillo;

import java.io.*;
import java.util.*;

public class EjecutaComandoParte5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un comando del sistema: ");
        String comandoUsuario = sc.nextLine().trim();

        // Si el usuario no escribe nada, terminamos
        if (comandoUsuario.isEmpty()) {
            System.out.println("No se ingresó ningún comando.");
            return;
        }

        // Dividir el comando en sus partes (tokens) por espacios
        // Por ejemplo: "ping google.com" -> ["ping", "google.com"]
        String[] comando = comandoUsuario.split(" ");

        // Crear el ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(comando);
        pb.redirectErrorStream(true); // Combina la salida de error y la estándar

        try {
            Process proceso = pb.start();

            // Leer y mostrar la salida (o errores)
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            // Código de salida del proceso
            int exitCode = proceso.waitFor();
            System.out.println("\nProceso terminado con código: " + exitCode);

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al ejecutar el comando: " + e.getMessage());
        }
    }
}

