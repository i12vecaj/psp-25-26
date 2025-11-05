/**
 * FR2: Con sincronización usando Runnable
 */
public class ua2tarea1fr2runnable {

    private static int contador = 0;
    private static final Object lock = new Object();

    static class Tarea implements Runnable {
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    synchronized (lock) {
                        contador++;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread[] hilos = new Thread[5];

            for (int i = 0; i < 5; i++) {
                hilos[i] = new Thread(new Tarea());
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            System.out.println("Esperado: 5000");
            System.out.println("Obtenido: " + contador);
            System.out.println("\nThread vs Runnable:");
            System.out.println("Thread: extends Thread");
            System.out.println("Runnable: implements Runnable (más flexible)");
            System.out.println("Ambos dan el mismo resultado correcto");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}