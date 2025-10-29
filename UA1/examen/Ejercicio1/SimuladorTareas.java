 /*
 * Nombre: Daniel Ronda Morales
 * Fecha: [29/10/2023]
 * Descripción: [Ejercicio 1 de procesos e hilos]
 * FR implementados: []
 */
public class SimuladorTareas{
    
	public static class Tarea implements Runnable {
        private final String nombre;

        public Tarea(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            String hiloNombre = Thread.currentThread().getName();
            System.out.println(nombre + " ejecutándose en el hilo " + hiloNombre);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println( nombre + "interrumpida.");
            }

            System.out.println(nombre + " finalizada.");
        }
    }

    public static void main(String[] args) {
        
        Thread t11 = new Thread(new Tarea("Tarea 1"), "Hilo-1");
        Thread t12 = new Thread(new Tarea("Tarea 1"), "Hilo-2");
        Thread t13 = new Thread(new Tarea("Tarea 1"), "Hilo-3");
        Thread t21 = new Thread(new Tarea("Tarea 2"), "Hilo-1");
        Thread t22 = new Thread(new Tarea("Tarea 2"), "Hilo-2");
        Thread t23 = new Thread(new Tarea("Tarea 2"), "Hilo-3");
        Thread t31 = new Thread(new Tarea("Tarea 3"), "Hilo-1");
        Thread t32 = new Thread(new Tarea("Tarea 3"), "Hilo-2");
        Thread t33 = new Thread(new Tarea("Tarea 3"), "Hilo-3");

        t11.start();
        t12.start();
        t13.start();
        t21.start();
        t22.start();
        t23.start();
        t31.start();
        t32.start();
        t33.start();
    }


}

	/*
	
	- Proceso: es una instancia de ejecución con su propio espacio de direcciones (memoria), recursos y contexto
	de ejecución. Crear un proceso normalmente implica más sobrecarga y aislamiento
	  
    - Hilo: es una unidad de ejecución dentro de un proceso. Los hilos de un mismo proceso comparten el mismo
	espacio de direcciones y recursos

	Ventajas: Mejor utilización de CPU en sistemas con múltiples núcleos, permite que tareas independientes
    y mejora la capacidad de respuesta de aplicaciones interactivas

	Inconvenientes:La concurrencia introduce complejidad, es necesario sincronizar el acceso a recursos compartidos
	depuración y testing son más complicados que en código secuencial

	*/

    

