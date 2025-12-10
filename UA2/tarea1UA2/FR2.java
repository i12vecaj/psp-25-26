public class FR2 {
    private static int C = 0;
    private static final int H = 5;
    private static final int I = 1000;

    private static synchronized void inc() {
        C++;
    }

    private static class HiloSincronizado extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < I; i++) {
                inc();
            }
        }
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[H];

        for (int i = 0; i < H; i++) {
            hilos[i] = new HiloSincronizado();
            hilos[i].start();
        }

        try {
            for (int i = 0; i < H; i++) {
                hilos[i].join();
            }
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        System.out.println("Esperado: " + (H * I));
        System.out.println("Obtenido: " + C);
        System.out.println("Reflexión: El resultado es correcto gracias a synchronized.");
    }
}