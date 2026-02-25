public class Tarea2Runneable {
    // Instancia de la clase que contiene la lógica de incremento.
    private static ContadorSincronizado contadorSincronizado = new ContadorSincronizado();

    // Constante para el número de incrementos por hilo
    private static final int INCREMENTOS_POR_HILO = 1000;
    // Constante para el número de hilos
    private static final int NUM_HILOS = 5;

    public static void main(String[] args) {
        Thread[] hilos = new Thread[NUM_HILOS];

        System.out.println("\n--- FR2.2: Sincronización (Interfaz Runnable) ---");
        System.out.println("Valor inicial del contador: " + contadorSincronizado.getContador());
        System.out.println("Resultado esperado (ideal): " + (NUM_HILOS * INCREMENTOS_POR_HILO));

        // 1. Crear Runnable y lanzar los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            // Se crea una instancia de Runnable
            Runnable worker = new WorkerIncrementador("R_Hilo-" + i, INCREMENTOS_POR_HILO, contadorSincronizado);
            // Se envuelve el Runnable en un objeto Thread
            hilos[i] = new Thread(worker);
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

        // El resultado siempre será 5000 (o 50000 si se ejecuta después del FR2.1 sin resetear el contador)
        // en ambas implementaciones sincronizadas, cumpliendo el objetivo.
    }
}

/**
 * Clase que implementa Runnable.
 * Contiene la lógica que se ejecutará en cada hilo.
 */
class WorkerIncrementador implements Runnable {
    private final int numIncrementos;
    private final ContadorSincronizado contador; // Se usa la misma clase de sincronización del FR2.1

    public WorkerIncrementador(String nombre, int numIncrementos, ContadorSincronizado contador) {
        // En Runnable, el nombre se asigna al objeto Thread que lo envuelve
        // En este caso, lo usamos solo como etiqueta interna si es necesario
        this.numIncrementos = numIncrementos;
        this.contador = contador; // Referencia compartida
    }

    @Override
    public void run() {
        // Control de errores básico: Captura de excepciones no comprobadas
        try {
            for (int i = 0; i < numIncrementos; i++) {
                contador.incrementar(); // Llama al método sincronizado
            }
        } catch (Exception e) {
            System.err.println("Error no controlado en " + Thread.currentThread().getName() + ": " + e.getMessage());
        }
    }
}
