/*
 * Crea un programa que reciba a través de sus argumentos una lista de ficheros de texto (procura que sean de un cierto tamaño, por ejemplo El Quijote.txt) y cuente el número de caracteres que hay en cada fichero (ejecución secuencial).
 * FR2: Modifica el programa para que cree un hilo para cada fichero (ejecución concurrente) - 2 puntos. 
 * Quiero hacer el FR2 ahora mismo
 * 
 * me hace falta donde meter los nombres de los ficheros
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorFichero extends Thread {
    private final String nombreFichero;
    private int contadorCaracteres;

    public LectorFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        this.contadorCaracteres = 0;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            int c;
            while ((c = br.read()) != -1) {
                contadorCaracteres++;
            }
            System.out.println("Fichero: " + nombreFichero + " - Caracteres: " + contadorCaracteres);
        } catch (IOException e) {
            System.err.println("Error leyendo el fichero " + nombreFichero + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, proporciona al menos un fichero de texto como argumento.");
            return;
        }

        Thread[] hilos = new Thread[args.length];

        for (int i = 0; i < args.length; i++) {
            hilos[i] = new LectorFichero(args[i]);
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error al esperar por los hilos: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}