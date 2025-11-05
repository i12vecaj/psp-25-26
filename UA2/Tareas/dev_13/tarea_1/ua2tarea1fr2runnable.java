public class ua2tarea1fr2runnable {

    static class Counter {
        private int value = 0;
        synchronized void increment() {
            value++;
        }
        synchronized int get() {
            return value;
        }
    }

    static class IncRunnable implements Runnable {
        private final Counter counter;
        private final int times;

        IncRunnable(Counter counter, int times) {
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
        Thread[] threads = new Thread[THREADS];

        // Lanzar hilos usando Runnable
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(new IncRunnable(counter, PER_THREAD));
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
        System.out.println("FR2 (synchronized, usando Runnable)");
        System.out.println("Esperado: " + expected + "  Resultado: " + actual);

        /*
         Observaciones:
         - El uso de Runnable no cambia la semántica de sincronización: si el
           método increment está synchronized, el resultado será correcto.
         - Diferencia práctica: Runnable permite separar tarea y ejecución y
           puede ser reutilizado por pools de hilos.
        */
    }
}
