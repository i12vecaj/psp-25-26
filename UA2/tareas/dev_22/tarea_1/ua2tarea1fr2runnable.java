/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Unidad: UA2 - Tarea 1 FR2 con Runnable
 */

public class ua2tarea1fr2runnable {

    private static int contador = 0;
    private static final int NUM_HILOS = 5;
    private static final int INCREMENTOS = 1000;

    public static void main(String[] args) {

        Runnable tarea = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < INCREMENTOS; j++) {
                    synchronized (ua2tarea1fr2runnable.class) {
                        contador++;
                    }
                }
            }
        };

        Thread[] hilos = new Thread[NUM_HILOS];

        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Thread(tarea);
            hilos[i].start();
        }

        // Esperar a que terminen todos los hilos
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar al hilo: " + e.getMessage());
            }
        }

        System.out.println("Valor esperado: " + (NUM_HILOS * INCREMENTOS));
        System.out.println("Valor obtenido: " + contador);
        System.out.println("Runnable también funciona correctamente");
    }
}
