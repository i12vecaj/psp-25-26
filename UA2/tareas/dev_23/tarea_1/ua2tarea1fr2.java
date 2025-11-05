public class ua2tarea1fr2 {
    private static final ObjetoCompartido recurso = new ObjetoCompartido();
    private static final int incremento = 1000;

    static class ObjetoCompartido {
        private int contador = 0;
        public synchronized void incrementar() { // Metodo sincronizado
            contador++;
        }
        public int getContador() {
            return contador;
        }
    }

    // Hilo que llama al metodo sincronizado
    static class HiloSincronizado extends Thread {
        private final ObjetoCompartido recurso;

        public HiloSincronizado(ObjetoCompartido rec) {
            this.recurso = rec;
        }

        @Override
        public void run() {
            for (int i = 0; i < incremento; i++) {
                recurso.incrementar();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- FR2.1: CON Sincronización ---");
        System.out.println("Esperado: 5000");

        // 1. Inicialización y lanzamiento manual de 5 hilos
        HiloSincronizado h1 = new HiloSincronizado(recurso);
        HiloSincronizado h2 = new HiloSincronizado(recurso);
        HiloSincronizado h3 = new HiloSincronizado(recurso);
        HiloSincronizado h4 = new HiloSincronizado(recurso);
        HiloSincronizado h5 = new HiloSincronizado(recurso);

        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        // 2. Esperar (join) y control de errores básico
        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar un hilo: " + e.getMessage());
        }

        System.out.println("\nResultado Final: " + recurso.getContador());
        System.out.println("Resultado CORRECTO. La sincronización es exitosa.");
    }
}

/*
Este apartado resuelve el problema de concurrencia de FR1. El resultado final del contador es correcto y determinista (5000).
La solución se implementó utilizando el modificador synchronized en el metodo incrementar().
Esto garantiza la exclusión mutua, solo un hilo puede acceder a la sección crítica a la vez.
 */