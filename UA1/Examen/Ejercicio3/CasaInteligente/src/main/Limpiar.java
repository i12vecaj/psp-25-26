 /*
 * Nombre: [Álvaro Caler Montes]
 * Fecha: [29/10/2025]
 * Descripción: [Ejercicio 3 del examen]
 * FR implementados: [FR1, FR2...]
 */

package main;

public class Limpiar implements Runnable{

	@Override
	public void run() {

		System.out.println("[Limpiar] Comenzando tarea...");
		
		try {
			
			Thread.sleep(3000);
			
		} catch (InterruptedException e) {

			e.printStackTrace();
			
		}
		
		System.out.println("[Limpiar] Tarea finalizada.");
		
	}

}
