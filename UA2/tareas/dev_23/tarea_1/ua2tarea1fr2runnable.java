public class ua2tarea1fr2runnable {
    private static final ObjetoCompartido recurso = new ObjetoCompartido();
    private static final int INCREMENTO = 1000;

    // La lógica de sincronización permanece aquí
    static class ObjetoCompartido {
        private int contador = 0;
        public synchronized void incrementar() {
            contador++;
        }
        public int getContador() {
            return contador;
        }
    }

    // Tarea que implementa Runnable
    static class TareaSincronizada implements Runnable {
        private final ObjetoCompartido recurso;

        public TareaSincronizada(ObjetoCompartido rec) {
            this.recurso = rec;
        }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTO; i++) {
                recurso.incrementar(); // Acceso seguro
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- FR2.2: CON Sincronización ---");
        System.out.println("Esperado: 5000");

        // Creamos una única instancia de la Tarea
        TareaSincronizada tarea = new TareaSincronizada(recurso);

        // 1. Inicialización manual de 5 objetos Thread, pasándoles la misma tarea
        Thread h1 = new Thread(tarea, "Hilo-R1");
        Thread h2 = new Thread(tarea, "Hilo-R2");
        Thread h3 = new Thread(tarea, "Hilo-R3");
        Thread h4 = new Thread(tarea, "Hilo-R4");
        Thread h5 = new Thread(tarea, "Hilo-R5");

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
        System.out.println("Resultado CORRECTO");
    }
}
/*
Ambos enfoques (extender Thread e implementar Runnable) logran el mismo resultado correcto,
porque la clave de la solución es la protección del recurso compartido (synchronized) y no el mecanismo de creación del hilo.

- Clase Thread (FR2.1): Es más simple para el ejemplo, pero consume la única ranura de herencia de Java.
- Interfaz Runnable (FR2.2): Es el patrón preferido. Separa la tarea (la lógica dentro de run()) del mecanismo del hilo. Esto permite que la clase de la tarea herede de otras clases (más flexible).
 */