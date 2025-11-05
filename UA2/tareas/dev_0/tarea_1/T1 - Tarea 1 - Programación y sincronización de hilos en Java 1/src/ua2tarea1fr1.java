/**
 * FR1: Sin sincronización
 * Demuestra el problema de concurrencia
 */
public class ua2tarea1fr1 {

    private static int contador = 0;

    static class Hilo extends Thread {
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    contador++; // Problema: no es atómico
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread[] hilos = new Thread[5];

            // Crear y lanzar hilos
            for (int i = 0; i < 5; i++) {
                hilos[i] = new Hilo();
                hilos[i].start();
            }

            // Esperar
            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            // Resultado
            System.out.println("Esperado: 5000");
            System.out.println("Obtenido: " + contador);
            System.out.println("\n¿Resultado esperado? NO");
            System.out.println("Causa: Race condition (varios hilos acceden sin control)");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}