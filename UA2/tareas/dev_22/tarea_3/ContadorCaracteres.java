/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Unidad: UA2 - Tarea 3 ContadorCaracteres
 *
 *El programa cuenta caracteres primero de forma secuencial y luego concurrente
 * con hilos y compara los tiempos
 */

import java.io.File;

public class ContadorCaracteres {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Error: No se han pasado ficheros como argumentos.");
            System.err.println("Uso: java ContadorCaracteres fichero1.txt fichero2.txt ...");
            return;
        }

        // Validación de ficheros
        for (String f : args) {
            File archivo = new File(f);
            if (!archivo.exists() || !archivo.isFile()) {
                System.err.println("Error: El fichero " + f + " no existe o no es válido.");
                return;
            }
        }

        // La ejecucion secuencial

        System.out.println("Ejecucion secuencial ");
        long inicioSecuencial = System.currentTimeMillis();

        for (String fichero : args) {
            try {
                int total = ContadorFichero.contarCaracteres(fichero);
                System.out.println("El fichero " + fichero + " contiene " + total + " caracteres.");
            } catch (Exception e) {
                System.err.println("Error en el fichero: " + fichero);
            }
        }

        long finSecuencial = System.currentTimeMillis();
        long tiempoSecuencial = finSecuencial - inicioSecuencial;

        System.out.println("\nTiempo total secuencial: " + tiempoSecuencial);


        // La ejecucion concurrente

        System.out.println("\n Ejecucion concurrente ");
        long inicioConcurrente = System.currentTimeMillis();

        Thread[] hilos = new Thread[args.length];

        for (int i = 0; i < args.length; i++) {
            hilos[i] = new HiloContador(args[i]);
            hilos[i].start();
        }

        // Esperar a que terminen todos los hilos
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error al esperar a un hilo.");
            }
        }

        long finConcurrente = System.currentTimeMillis();
        long tiempoConcurrente = finConcurrente - inicioConcurrente;

        System.out.println("\nTiempo total concurrente: " + tiempoConcurrente);

        System.out.println("\n Resultado: ");
        System.out.println("Secuencial:  " + tiempoSecuencial);
        System.out.println("Concurrente: " + tiempoConcurrente);

        if (tiempoConcurrente < tiempoSecuencial) {
            System.out.println("La ejecución concurrente fue más rápida.");
        } else {
            System.out.println("La ejecución secuencial fue más rápida.");
        }
    }
}
