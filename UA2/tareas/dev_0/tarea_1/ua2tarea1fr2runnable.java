public class ContadorSincronizadoRunnable implements Runnable {

    static int contador = 0;
    static final Object lock = new Object();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (lock) {
                contador++;
            }
        }
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];
        ContadorSincronizadoRunnable tarea = new ContadorSincronizadoRunnable();

        // Lanzamos 5 hilos con la misma tarea Runnable
        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(tarea);
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error: hilo interrumpido.");
            }
        }

        System.out.println("Valor final del contador (sincronizado con Runnable): " + contador);
    }
}
