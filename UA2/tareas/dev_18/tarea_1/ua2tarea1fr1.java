// FR1 - Programa sin sincronización
// Cada uno de 5 hilos incrementa la variable compartida "contador" 1000 veces.

public class ua2tarea1fr1 {
    // variable compartida por todos los hilos
    private static int contador = 0;

    static class Incrementador extends Thread {
        private final int veces;

        Incrementador(int veces) {
            this.veces = veces;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < veces; i++) {
                    // acceso NO sincronizado -> condición de carrera
                    contador++;
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

        // lanzar hilos
        for (int i = 0; i < HILOS; i++) {
            hilos[i] = new Incrementador(INCREMENTOS);
            hilos[i].setName("Inc-" + i);
            hilos[i].start();
        }

        // esperar a que terminen
        for (int i = 0; i < HILOS; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.err.println("Main interrumpido esperando hilo " + hilos[i].getName());
                Thread.currentThread().interrupt(); // restablecer estado de interrupción
            }
        }

        System.out.println("Valor final contador (NO sincronizado): " + contador);
    }
}
