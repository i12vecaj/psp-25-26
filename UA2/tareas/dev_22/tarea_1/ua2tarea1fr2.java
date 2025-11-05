/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Unidad: UA2 - Tarea 1 FR2
 */

public class ua2tarea1fr2 {

    private static int contador = 0;
    private static final int NUM_HILOS = 5;
    private static final int INCREMENTOS = 1000;

    // Método sincronizado para evitar condiciones de carrera
    private static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {

        Thread[] hilos = new Thread[NUM_HILOS];

        // Crear y lanzar los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTOS; j++) {
                    incrementar();
                }
            });
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
        System.out.println("Con sincronización, el resultado es correcto.");
    }
}