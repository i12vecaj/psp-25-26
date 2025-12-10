public class FR2Runnable {
    private static class Contenedor {
        private int C = 0;

        public void inc() {
            synchronized (this) {
                C++;
            }
        }

        public int getC() {
            return C;
        }
    }

    private static final int H = 5;
    private static final int I = 1000;

    private static class Tarea implements Runnable {
        private final Contenedor cnt;

        public Tarea(Contenedor cnt) {
            this.cnt = cnt;
        }

        @Override
        public void run() {
            for (int i = 0; i < I; i++) {
                cnt.inc();
            }
        }
    }

    public static void main(String[] args) {
        Contenedor c = new Contenedor();
        Thread[] hilos = new Thread[H];

        for (int i = 0; i < H; i++) {
            hilos[i] = new Thread(new Tarea(c));
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
        System.out.println("Obtenido: " + c.getC());
        System.out.println("Reflexión: El resultado es correcto usando Runnable y synchronized.");
    }
}