 /*
 * Nombre: [Álvaro Caler Montes]
 * Fecha: [29/10/2025]
 * Descripción: [Ejercicio 3 del examen]
 * FR implementados: [FR1, FR2...]
 */

package main;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Iniciando programa: \n");
		
		Thread lavarropa = new Thread(new LavarRopa());
		Thread cocinar = new Thread(new Cocinar());
		Thread limpiar = new Thread(new Limpiar());

		lavarropa.start();
		cocinar.start();
		limpiar.start();
						
	}
	
}
