package Parte5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class TerminalInteractiva {
    public static void main(String[] args) {
        Scanner lectorTeclado = new Scanner(System.in);
        System.out.println("--- Ejecutor de Comandos del Sistema ---");
        System.out.println("ADVERTENCIA: Sea cuidadoso con el comando que ingresa.");
        System.out.print("Introduce un comando (ej: ls -l, cmd /c dir, echo Hola): ");

        String rawInput = lectorTeclado.nextLine().trim();
        lectorTeclado.close();

        if (rawInput.length() == 0) {
            System.out.println("No se introdujo ningún comando.");
            return;
        }

        // Tokenización de la cadena ingresada dividiendo por bloques de espacios
        String[] matrizComando = rawInput.split("\\s+");

        ProcessBuilder creadorProcesos = new ProcessBuilder(Arrays.asList(matrizComando));
        creadorProcesos.redirectErrorStream(true);

        try {
            System.out.println("\n--- Ejecutando: " + rawInput + " ---");
            Process runtimeProceso = creadorProcesos.start();

            try (BufferedReader streamLector = new BufferedReader(new InputStreamReader(runtimeProceso.getInputStream()))) {
                String trazaLinea;
                while ((trazaLinea = streamLector.readLine()) != null) {
                    System.out.println(trazaLinea);
                }
            }

            int valorSalidaSO = runtimeProceso.waitFor();
            System.out.println("\n--- Comando finalizado con código de salida: " + valorSalidaSO + " ---");

        } catch (IOException excepcionSistema) {
            System.err.println("\nERROR: No se pudo ejecutar el comando. Asegúrese de que el comando base sea válido.");
            System.err.println("Detalle del error: " + excepcionSistema.getMessage());
        } catch (InterruptedException excepcionHilos) {
            System.err.println("El proceso fue interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}