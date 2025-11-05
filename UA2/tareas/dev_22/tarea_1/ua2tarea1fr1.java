/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Unidad: UA2 - Tarea 1 FR1
 */

public class ua2tarea1fr1 {

    // Variable compartida por todos los hilos
    private static int contador = 0;

    // Número de hilos e incrementos
    private static final int NUM_HILOS = 5;
    private static final int INCREMENTOS = 1000;

    public static void main(String[] args) {

        Thread[] hilos = new Thread[NUM_HILOS];

        // Crear y lanzar los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTOS; j++) {
                    contador++;
                }
            });
            hilos[i].start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error: un hilo fue interrumpido -> " + e.getMessage());
            }
        }

        // Mostrar resultado
        System.out.println("Valor esperado: " + (NUM_HILOS * INCREMENTOS));
        System.out.println("Valor obtenido: " + contador);
        System.out.println("Como no hay sincronización, el resultado suele ser menor al esperado.");
    }
}