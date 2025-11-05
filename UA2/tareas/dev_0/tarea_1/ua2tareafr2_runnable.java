package UA2.tareas.dev_0.tarea_1;

public class ua2tareafr2_runnable {
private int contador = 0;

// Método sincronizado para evitar condiciones de carrera
public synchronized void incrementar() {
    contador++;
}

// Clase interna que implementa Runnable
static class HiloRunnable implements Runnable {
    private ua2tareafr2_runnable tarea;

    public HiloRunnable(ua2tarea1_runnable tarea) {
        this.tarea = tarea;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 1000; i++) {
                tarea.incrementar();
            }
        } catch (Exception e) {
            System.err.println("Error en hilo: " + e.getMessage());
        }
    }
}

// Método principal
public static void main(String[] args) {
    ua2tarea1_runnable tareaCompartida = new ua2tarea1_runnable();

    // Crear hilos con Runnable
    Thread t1 = new Thread(new HiloRunnable(tareaCompartida));
    Thread t2 = new Thread(new HiloRunnable(tareaCompartida));
    Thread t3 = new Thread(new HiloRunnable(tareaCompartida));
    Thread t4 = new Thread(new HiloRunnable(tareaCompartida));
    Thread t5 = new Thread(new HiloRunnable(tareaCompartida));

    t1.start();
    t2.start();
    t3.start();
    t4.start();
    t5.start();

    try {
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    } catch (InterruptedException e) {
        System.err.println("Un hilo fue interrumpido: " + e.getMessage());
        Thread.currentThread().interrupt();
    }

    System.out.println("Valor final del contador (Runnable): " + tareaCompartida.contador);
}
}