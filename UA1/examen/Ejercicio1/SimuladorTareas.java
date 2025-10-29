 /*
  * Nombre: Jonathan Martínez Pullana
  * Fecha: 29/10/2025
  * Descripción: Simulador de tareas
  * FR implementados: a) b) c) d) e) f)
  */

public class SimuladorTareas {
    // a)
    public static class Tarea implements Runnable {

        private String nombre;
        private int tiempo;

        // Constructor
        public Tarea(String nombre, int tiempo) {
            this.nombre = nombre;
            this.tiempo = tiempo;
        }

        // b)
        @Override
        public void run() {
            System.out.println("[" + nombre + "] Comenzando... Hilo: " + Thread.currentThread().getName());

                System.out.println("[" + nombre + "] Tiempo " + tiempo);
                try {
                    Thread.sleep(tiempo); // Establecemos de manera manual el tiempo que queremos.
                }
                catch (InterruptedException e) {
                    System.err.println("[" + nombre + "] Hilo interrumpido: " + e.getMessage());
                }

            System.out.println("[" + nombre + "] Ha terminado.");
        }
    }


    public static void main() {
        System.out.println("=== Simulador de tareas ===");
        System.out.println("Hilo principal: " + Thread.currentThread().getName());

        // c)
        Thread Tarea1 = new Thread(new Tarea("Tarea 1", 500));
        Thread Tarea2 = new Thread(new Tarea("Tarea 2", 1000));
        Thread Tarea3 = new Thread(new Tarea("Tarea 3", 1500));
        
        // d)
        Tarea1.start();
        Tarea2.start();
        Tarea3.start();

        // e) Nos aseguramos de que las tareas terminen antes de terminar el programa.
        try {
            Tarea1.join();
            Tarea2.join();
            Tarea3.join();
        } catch (InterruptedException e) {
            System.err.println("Hilo principal interrumpido: " + e.getMessage());
        }

        System.out.println("Hilo principal: " + Thread.currentThread().getName() + " ha terminado");
    }
}

/*
f)

Qué diferencia hay entre crear un proceso y crear un hilo.

Un proceso es la instancia en ejecución de un programa, que tiene su propio espacio de memoria y recursos del sistema.
Un hilo, es como un subproceso, entre ellos comparten el mismo espacio de memoria y recursos
con otros hilos del mismo proceso. Los hilos son más ligeros pudiendo crear rápidamente y de manera flexible varios
y permiten una comunicación más rápida entre ellos, pero también pueden causar problemas de sincronización si no se manejan adecuadamente.
----------------------------------------------------------------------------------------------------
Qué ventajas e inconvenientes tiene la programación concurrente.

VENTAJAS:
La programación concurrente permite que varios programas se ejecuten simultaneamente (pero no al mismo tiempo real)

- mejora la eficiencia y el rendimiento tratándose de programas multihilo/multinúcleo. (Comparten memoria y eso)

- Mejor utilización de los recursos del sistema y puede mejorar la capacidad de respuesta de las aplicaciones. (Como juegos)

DESVENTAJAS:
- problemas de sincronización
- diseño más complejo
- un solo fallo puede afectar al programa entero

 */