public class Tarea2 {
    // Instancia de la clase que contiene la lógica de incremento.
    private static ContadorSincronizado contadorSincronizado = new ContadorSincronizado();

    // Constante para el número de incrementos por hilo
    private static final int INCREMENTOS_POR_HILO = 1000;
    // Constante para el número de hilos
    private static final int NUM_HILOS = 5;

    public static void main(String[] args) {
        Thread[] hilos = new Thread[NUM_HILOS];

        System.out.println("\n--- FR2.1: Sincronización (Clase Thread) ---");
        System.out.println("Valor inicial del contador: " + contadorSincronizado.getContador());
        System.out.println("Resultado esperado (ideal): " + (NUM_HILOS * INCREMENTOS_POR_HILO));

        // 1. Crear y lanzar los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            // Se pasa la misma instancia del contador a todos los hilos
            hilos[i] = new HiloSincronizadoThread("S_Hilo-" + i, INCREMENTOS_POR_HILO, contadorSincronizado);
            hilos[i].start();
        }

        // 2. Esperar a que todos los hilos terminen (join)
        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido.");
            Thread.currentThread().interrupt();
        }

        // 3. Comprobar el resultado final
        System.out.println("Valor final del contador (obtenido): " + contadorSincronizado.getContador());
    }
}

/**
 * Clase que encapsula la variable y el método de incremento sincronizado.
 * El uso de 'synchronized' actúa sobre el objeto 'this' (la instancia de ContadorSincronizado).
 */
class ContadorSincronizado {
    private int contador = 0;

    /**
     * Método sincronizado: solo un hilo puede ejecutar este método a la vez
     * en una instancia dada de ContadorSincronizado.
     */
    public synchronized void incrementar() {
        contador++;
    }

    public int getContador() {
        return contador;
    }
}

/**
 * Hilo que llama al método sincronizado de la clase ContadorSincronizado.
 */
class HiloSincronizadoThread extends Thread {
    private final int numIncrementos;
    private final ContadorSincronizado contador;

    public HiloSincronizadoThread(String nombre, int numIncrementos, ContadorSincronizado contador) {
        super(nombre);
        this.numIncrementos = numIncrementos;
        this.contador = contador; // Referencia compartida
    }

    @Override
    public void run() {
        // Control de errores básico: Captura de excepciones no comprobadas (ej. NullPointerException)
        try {
            for (int i = 0; i < numIncrementos; i++) {
                contador.incrementar(); // Llama al método sincronizado
            }
        } catch (Exception e) {
            System.err.println("Error no controlado en " + getName() + ": " + e.getMessage());
        }
    }
}
