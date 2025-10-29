 /*
 * Nombre: [Álvaro Caler Montes]
 * Fecha: [29/10/2025]
 * Descripción: [Ejercicio 1 examen]
 * FR implementados: [FR1, FR2...]
 */

package main;

public class SimuladorTareas {

	public static void main(String[] args) {
		
		
		Thread tarea1 = new Thread(new Tarea ("Tarea 1"));
		Thread tarea2 = new Thread(new Tarea ("Tarea 2"));
		Thread tarea3 = new Thread(new Tarea ("Tarea 3"));	
		
		tarea1.start();
		tarea2.start();
		tarea3.start();
		
	}

	
	/* Un proceso es más pesado que un hilo, por lo que va más lento, además, cada proceso es independiente entre sí, por lo que si uno da error el resto
	   no se verán afectados. */
	
	/*  Un hilo es más ligero que un proceso, por lo que va más rapido, pero estos comparten memoria, por lo que si uno da error el resto se verán afectados. */
	
	/* La programación concurrente permite ejecutar varias tareas al mismo tiempo, pero no a la vez, por lo que cada tarea, "peleará" por tener
	   más memoria que otra para terminar de ejecutarse. */
	
}
