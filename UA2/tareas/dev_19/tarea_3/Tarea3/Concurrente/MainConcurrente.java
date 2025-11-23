package Tarea3.Concurrente;

/*
FR2: Crea un programa que reciba a través de sus argumentos una lista de ficheros de texto
y cree un hilo para cada fichero (ejecución concurrente).
*/

public class MainConcurrente {
    public static void main(String[] args) {
        //se verifica que se hayan pasado al menos dos ficheros como argumentos
        if (args.length < 2) {
            System.err.println("Error: Debes proporcionar al menos dos ficheros de texto como argumentos.");
            return;
        }

        //se guarda el tiempo de inicio para medir cuánto tarda el procesamiento concurrente.
        long t_comienzo = System.currentTimeMillis();

        // un array de hilos
        Thread[] hilos = new Thread[args.length];

        for (int i = 0; i < args.length; i++) {
            ContadorCaracteresRunnable tarea = new ContadorCaracteresRunnable(args[i]);
            hilos[i] = new Thread(tarea);

            // Lanzamos el hilo para que empiece a trabajar en paralelo
            hilos[i].start();
        }

        // se esperan a que todos los hilos terminen antes de calcular el tiempo total
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error: hilo interrumpido.");
            }
        }

        // Calculamos el tiempo total de ejecución concurrentex
        long t_fin = System.currentTimeMillis();
        System.out.println("Tiempo total (concurrente): " + (t_fin - t_comienzo) + " ms");
    }
}
