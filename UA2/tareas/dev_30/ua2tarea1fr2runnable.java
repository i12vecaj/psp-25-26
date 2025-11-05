public class ua2tarea1fr2runnable {
    static int contador = 0;
    public static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // crear los hilos con Runnable y lanzarlos
        for (int i = 0; i < 5; i++) {
            Runnable tarea = new MiRunnable("Hilo-" + (i + 1));
            hilos[i] = new Thread(tarea);
            hilos[i].start();
        }

        // Esperar a que todos terminen
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        // Resultado final
        System.out.println("Valor final del contador: " + contador);
        System.out.println("Valor esperado: 5000");
    }

    static class MiRunnable implements Runnable {
        private String nombre;

        public MiRunnable(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                ua2tarea1fr2.incrementar(); // llamada sincronizada
            }
        }
    }
}
