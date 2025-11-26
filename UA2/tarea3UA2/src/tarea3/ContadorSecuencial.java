package tarea3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ContadorSecuencial {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java tarea3.ContadorSecuencial <fichero1> <fichero2> ...");
            return;
        }

        long t_comienzo, t_fin;
        t_comienzo = System.currentTimeMillis();

        long totalCaracteres = 0;

        for (String rutaFichero : args) {
            long caracteresFichero = contarCaracteres(rutaFichero);
            if (caracteresFichero != -1) {
                System.out.println("Fichero: " + rutaFichero + ", Caracteres: " + caracteresFichero);
                totalCaracteres += caracteresFichero;
            }
        }

        t_fin = System.currentTimeMillis();
        long t_total = t_fin - t_comienzo;

        System.out.println("---");
        System.out.println("Total de caracteres de todos los ficheros: " + totalCaracteres);
        System.out.println("Tiempo de ejecucion secuencial (ms): " + t_total);
    }

    private static long contarCaracteres(String rutaFichero) {
        long cuenta = 0;
        try (Scanner scanner = new Scanner(new File(rutaFichero))) {
            scanner.useDelimiter("");
            while (scanner.hasNext()) {
                scanner.next();
                cuenta++;
            }
            return cuenta;
        } catch (FileNotFoundException e) {
            System.err.println("Error: El fichero no se encuentra o no se puede leer: " + rutaFichero);
            return -1;
        }
    }
}