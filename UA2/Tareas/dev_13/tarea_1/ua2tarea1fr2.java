public class ua2tarea1fr2 {

    // Contador compartido con método synchronized
    static class Counter {
        private int value = 0;
        synchronized void increment() {
            value++;
        }
        synchronized int get() {
            return value;
        }
    }

    // Hilo que incrementa el contador N veces (subclase de Thread)
    static class IncThread extends Thread {
        private final Counter counter;
        private final int times;

        IncThread(Counter counter, int times) {
            this.counter = counter;
            this.times = times;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                counter.increment();
            }
        }
    }

    public static void main(String[] args) {
        final int THREADS = 5;
        final int PER_THREAD = 1000;

        Counter counter = new Counter();
        IncThread[] threads = new IncThread[THREADS];

        // Lanzar hilos (subclase Thread)
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new IncThread(counter, PER_THREAD);
            threads[i].start();
        }

        // Esperar a que terminen
        for (int i = 0; i < THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.err.println("Thread join interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        int expected = THREADS * PER_THREAD;
        int actual = counter.get();
        System.out.println("FR2 (synchronized, usando Thread)");
        System.out.println("Esperado: " + expected + "  Resultado: " + actual);

        /*
         Observaciones:
         - Al usar synchronized en increment(), la operación es atómica respecto
           a otros hilos y deberíamos obtener el resultado esperado: 5000.
         - Pequeña sobrecarga por la sincronización, pero garantiza corrección.
        */
    }
}
