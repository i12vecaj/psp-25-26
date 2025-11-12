// FR2 (parte 2) - Uso de Runnable + sincronización
// Misma lógica que ua2tarea1fr2 pero usando Runnable en vez de extender Thread.

public class ua2tarea1fr2runnable {
    private static int contador = 0;
    private static final Object LOCK = new Object();

    static class IncrementadorSyncRunnable implements Runnable {
        private final int veces;

        IncrementadorSyncRunnable(int veces) {
            this.veces = veces;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < veces; i++) {
                    synchronized (LOCK) {
                        contador++;
                    }
                }
            } catch (RuntimeException e) {
                System.err.println("Excepción en Runnable " + Thread.currentThread().getName());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final int HILOS = 5;
        final int INCREMENTOS = 1000;
        Thread[] hilos = new Thread[HILOS];

        for (int i = 0; i < HILOS; i++) {
            hilos[i] = new Thread(new IncrementadorSyncRunnable(INCREMENTOS), "RSync-" + i);
            hilos[i].start();
        }

        for (int i = 0; i < HILOS; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.err.println("Main interrumpido esperando hilo " + hilos[i].getName());
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Valor final contador (SINCRONIZADO - Runnable): " + contador);
    }
}
