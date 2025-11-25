import java.io.*;
import java.util.*;

public class App {

    public static long contarCaracteresSecuencial(String[] ficheros) {
        long tInicio = System.currentTimeMillis();

        for (String fichero : ficheros) {
            try {
                File file = new File(fichero);
                if (!file.exists() || !file.isFile()) {
                    System.err.println("No se puede acceder: " + fichero);
                    continue;
                }

                FileReader fr = new FileReader(file);
                int contador = 0;
                while (fr.read() != -1) contador++;
                fr.close();
                System.out.println("Secuencial - " + fichero + ": " + contador + " caracteres");

            } catch (IOException e) {
                System.err.println("Error: " + fichero);
            }
        }

        long tFin = System.currentTimeMillis();
        return tFin - tInicio;
    }

    static class HiloContador extends Thread {
        private final String fichero;

        HiloContador(String fichero) {
            this.fichero = fichero;
        }

        @Override
        public void run() {
            try {
                File file = new File(fichero);
                if (!file.exists() || !file.isFile()) {
                    System.err.println("No se puede acceder: " + fichero);
                    return;
                }

                FileReader fr = new FileReader(file);
                int contador = 0;
                while (fr.read() != -1) contador++;
                fr.close();
                System.out.println("Concurrente - " + fichero + ": " + contador + " caracteres");

            } catch (IOException e) {
                System.err.println(" Error: " + fichero);
            }
        }
    }

    public static long contarCaracteresConcurrente(String[] ficheros) {
        long tInicio = System.currentTimeMillis();

        List<Thread> hilos = new ArrayList<>();

        for (String fichero : ficheros) {
            Thread h = new HiloContador(fichero);
            hilos.add(h);
            h.start();
        }

        for (Thread h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                System.err.println("Hilo interrumpido");
            }
        }

        long tFin = System.currentTimeMillis();
        return tFin - tInicio;
    }

    public static void main(String[] args) {
        String fichero = "src/el_quijote.txt";
        String[] ficheros = { fichero };

        System.out.println("EJECUCIÓN SECUENCIAL");
        long tiempoSec = contarCaracteresSecuencial(ficheros);
        System.out.println("Tiempo secuencial: " + tiempoSec + " ms");

        System.out.println("\nEJECUCIÓN CONCURRENTE");
        long tiempoCon = contarCaracteresConcurrente(ficheros);
        System.out.println("Tiempo concurrente: " + tiempoCon + " ms");

        System.out.println("\nCOMPARACIÓN");
        System.out.println("Diferencia (Sec - Con): " + (tiempoSec - tiempoCon) + " ms");
    }
}