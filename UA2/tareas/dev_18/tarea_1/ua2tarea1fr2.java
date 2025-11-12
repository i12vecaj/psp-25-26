// FR2 (parte 1) - Uso de la clase Thread + sincronización
// Igual que FR1 pero el acceso a "contador" está sincronizado para evitar condiciones de carrera.

public class ua2tarea1fr2 {
    private static int contador = 0;
    // lock explícito para sincronizar
    private static final Object LOCK = new Object();

    static class IncrementadorSync extends Thread {
        private final int veces;

        IncrementadorSync(int veces) {
            this.veces = veces;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < veces; i++) {
                    // acceso sincronizado
                    synchronized (LOCK) {
                        contador++;
                    }
                }
            } catch (RuntimeException e) {
                System.err.println("Excepción en hilo " + getName());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final int HILOS = 5;
        final int INCREMENTOS = 1000;
        Thread[] hilos = new Thread[HILOS];

        for (int i = 0; i < HILOS; i++) {
            hilos[i] = new IncrementadorSync(INCREMENTOS);
            hilos[i].setName("TSync-" + i);
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

        System.out.println("Valor final contador (SINCRONIZADO - Thread): " + contador);
    }
}
