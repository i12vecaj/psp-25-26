/**
 * Programa Lanzador
 * -----------------
 * Ejecuta el programa Principal con distintos argumentos
 * y muestra en pantalla lo que ocurre según el valor devuelto.
 *
 * Autor: [Tu nombre]
 * Fecha: [Fecha de entrega]
 */

import java.io.*;

public class Lanzador {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Lanzador <argumento>");
            return;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("java", "Principal", args[0]);
            pb.inheritIO(); 
            Process proceso = pb.start();

            int exitCode = proceso.waitFor();

            switch (exitCode) {
                case 0:
                    System.out.println("Todo chachi");
                    break;
                case 1:
                    System.out.println("Error: no se proporcionaron argumentos.");
                    break;
                case 2:
                    System.out.println("no es un número entero.");
                    break;
                case 3:
                    System.out.println("el número es negativo.");
                    break;
                default:
                    System.out.println("error por defecto " + exitCode);
            }

        } catch (IOException e) {
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
        // le hace falta al proceso.waitFor(); por alguna razon
        } catch (InterruptedException e) {
            System.err.println("La ejecución fue interrumpida: " + e.getMessage());
        }
    }
}
