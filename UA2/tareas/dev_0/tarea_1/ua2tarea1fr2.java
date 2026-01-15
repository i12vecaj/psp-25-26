public class ContadorSincronizadoThread {

    static int contador = 0; // variable compartida

    // Objeto usado como candado para sincronizar
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // Crear hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        // Bloque sincronizado
                        synchronized (lock) {
                            contador++;
                        }
                    }
                }
            };
            hilos[i].start();
        }

        // Esperar a que terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error: hilo interrumpido.");
            }
        }

        System.out.println("Valor final del contador (sincronizado con Thread): " + contador);
    }
}
