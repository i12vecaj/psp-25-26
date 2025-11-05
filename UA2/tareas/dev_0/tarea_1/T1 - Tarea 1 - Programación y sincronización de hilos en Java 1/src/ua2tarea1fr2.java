
/**
 * FR2: Con sincronización usando Thread
 */
public class ua2tarea1fr2 {

    private static int contador = 0;
    private static final Object lock = new Object();

    static class Hilo extends Thread {
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    synchronized (lock) { // Solución
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
                hilos[i] = new Hilo();
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            System.out.println("Esperado: 5000");
            System.out.println("Obtenido: " + contador);
            System.out.println("\nDiferencia con FR1:");
            System.out.println("FR1: Resultado incorrecto");
            System.out.println("FR2: Resultado correcto (synchronized)");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}