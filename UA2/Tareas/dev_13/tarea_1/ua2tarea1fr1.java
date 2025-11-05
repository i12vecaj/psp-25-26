public class ua2tarea1fr1 {

    // Contador compartido (no sincronizado)
    static class Counter {
        int value = 0;
        void increment() {
            // Operación no atómica: lectura, suma y escritura
            value++;
        }
        int get() {
            return value;
        }
    }

    // Hilo que incrementa el contador N veces
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

        // Lanzar hilos
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new IncThread(counter, PER_THREAD);
            threads[i].start();
        }

        // Esperar a que terminen
        for (int i = 0; i < THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Control de errores básico: informar y restaurar el estado de interrupción
                System.err.println("Thread join interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        int expected = THREADS * PER_THREAD;
        int actual = counter.get();
        System.out.println("FR1 (sin sincronizar)");
        System.out.println("Esperado: " + expected + "  Resultado: " + actual);

        /*
         Observaciones:
         - Debido a que la operación value++ no es atómica, es probable que
           el resultado sea menor que 5000. Esto demuestra la condición de
           carrera: varias instrucciones (leer, sumar, escribir) se mezclan
           entre hilos y causan pérdida de incrementos.
        */
    }
}
