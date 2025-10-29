/*
 * Nombre: Pablo Herrador Castillo
 * Fecha: 29/10/2025
 * Descripción: Simulador Tareas
 * FR implementados: FR1, FR2 , FR3, FR4 , FR5
 */
 public class Main {

    // Clase interna que implementa Runnable
    public static class Tarea implements Runnable {
        private final String nombre;

        public Tarea(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            long hiloId = Thread.currentThread().getId();
            System.out.println("[" + nombre + "] ejecutándose en el hilo " + hiloId);

            
            int tiempo = 500 + (int) (Math.random() * 1001); 
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                
                Thread.currentThread().interrupt();
                System.out.println("[" + nombre + "] interrumpida en el hilo " + hiloId);
                return;
            }

            System.out.println("[" + nombre + "] finalizada.");
        }
    }

    public static void main(String[] args) {
        long mainId = Thread.currentThread().getId();
        System.out.println("Hilo principal id: " + mainId + " - antes de lanzar tareas");

        // Crear las tareas
        Thread t1 = new Thread(new Tarea("Tarea 1"));
        Thread t2 = new Thread(new Tarea("Tarea 2"));
        Thread t3 = new Thread(new Tarea("Tarea 3"));

        // Lanzar hilos
        t1.start();
        t2.start();
        t3.start();

        // Esperar a que terminen
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo principal interrumpido mientras esperaba a las tareas.");
        }

        long mainIdAfter = Thread.currentThread().getId();
        System.out.println("Hilo principal id: " + mainIdAfter + " - después de que todas las tareas han terminado");
    }
}

/*
 * El proceso es el espacio de memoria ocupado por ese proceso que se está ejecutando en ese momento actual. 
 * Mientras que el hilo son las diferentes partes que se ramifica el proceso y que puede hacerla a la vez de forma compartida. 
 * La programacion concurrente Ventajas: Mejorar la utilizacion de la CPU. Inconvenientes: Su complejidad a la hora de programar */
