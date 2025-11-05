public class Tarea1 {
    // Variable compartida estática.
    // Debe ser estática para ser la misma instancia para todos los hilos.
    public static int contador = 0;

    // Constante para el número de incrementos por hilo
    private static final int INCREMENTOS_POR_HILO = 1000;
    // Constante para el número de hilos
    private static final int NUM_HILOS = 5;

    // Control de errores básico
    public static void main(String[] args) {
        Thread[] hilos = new Thread[NUM_HILOS];

        System.out.println("--- FR1: Hilos sin Sincronización ---");
        System.out.println("Valor inicial del contador: " + contador);
        System.out.println("Incrementos esperados por hilo: " + INCREMENTOS_POR_HILO);
        System.out.println("Número de hilos: " + NUM_HILOS);
        System.out.println("Resultado esperado (ideal): " + (NUM_HILOS * INCREMENTOS_POR_HILO));

        // 1. Crear y lanzar los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new HiloIncrementador("Hilo-" + i, INCREMENTOS_POR_HILO);
            hilos[i].start();
        }

        // 2. Esperar a que todos los hilos terminen (join)
        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            // Manejo de la interrupción del hilo principal
            System.err.println("El hilo principal fue interrumpido mientras esperaba a los demás hilos.");
            Thread.currentThread().interrupt(); // Reestablecer el estado de interrupción
        }

        // 3. Comprobar el resultado final
        System.out.println("--- Reflexión ---");
        System.out.println("Valor final del contador (obtenido): " + contador);

        // Reflexión sobre el resultado
        if (contador == (NUM_HILOS * INCREMENTOS_POR_HILO)) {
            System.out.println("\n**Reflexión (Inusual):** El resultado SÍ coincide con el esperado. Esto es poco probable y generalmente se debe a la baja concurrencia en el entorno de ejecución.");
        } else {
            System.out.println("El resultado NO coincide con el esperado (" + (NUM_HILOS * INCREMENTOS_POR_HILO) + ").");
            System.out.println("Esto se debe a las **condiciones de carrera** (race conditions).");
            System.out.println("El incremento (contador++) no es una operación atómica; consta de 3 pasos (leer, incrementar, escribir).");
            System.out.println("Varios hilos leen el mismo valor, lo incrementan en su CPU y luego lo escriben, solapando las operaciones y perdiendo incrementos.");
        }
    }
}
class HiloIncrementador extends Thread {
    private final int numIncrementos;

    public HiloIncrementador(String nombre, int numIncrementos) {
        super(nombre);
        this.numIncrementos = numIncrementos;
    }

    @Override
    public void run() {
        for (int i = 0; i < numIncrementos; i++) {
            // Operación no atómica que causa la condición de carrera
            Tarea1.contador++;
        }
    }
}

// Ejemplo de salida (el valor final varía en cada ejecución, pero es menor a 5000):
// --- FR1: Hilos sin Sincronización ---
// Valor inicial del contador: 0
// Incrementos esperados por hilo: 1000
// Número de hilos: 5
// Resultado esperado (ideal): 5000


