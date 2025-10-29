 /*
 * Nombre: [Álvaro Caler Montes]
 * Fecha: [29/10/2025]
 * Descripción: [Ejercicio 1 examen]
 * FR implementados: [FR1, FR2...]
 */

package main;

public class Tarea implements Runnable{

	String nombre;
	
	public Tarea(String nombre) {
	
		this.nombre = nombre;
		
	}

	
	@Override
	public void run() {
		
		for (int i = 0; i < 10; i++) {
			
			System.out.println(nombre + " se está ejecutando en el hilo " + (i + 1) + ".");
			
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {

				e.printStackTrace();
				
			}
			
		} 
			
		System.out.println("[" + nombre + "] "+ "finalizada.");
	
	}
	
}