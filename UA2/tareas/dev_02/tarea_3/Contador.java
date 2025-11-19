package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Contador implements Runnable{

	String nombre;			// Variable en la que se guardara el nombre del documento

	public Contador(String nombre) {

		this.nombre = nombre;			// Constructor de la clase
		
	}
	

	@Override
	public void run() {

		
		FileReader fr;				// Lector de archivos para leer el documento
		
		int caracteres = 0;			// Variable para pasar por los caracteres del documento
		
		int contador = 0; 		// Contador de caracteres
		
		long t_comienzo, t_fin;				
		t_comienzo = System.currentTimeMillis();		// Se empieza a contar el tiempo de ejecucion
		
		
		try {			// Estructura de control
			
			fr = new FileReader(nombre); // Nombre del documento a leer
			
			while (caracteres != -1) {    		// Leer hasta que no queden más caracteres
				
				try {		// Estructura de control
					
					caracteres = fr.read();		// El valor de caracteres pasa a ser el del siguiente caracter
					contador ++;		// Incrementar el valor del contador
					
				} catch (IOException e) {

					e.printStackTrace();		//Muestra el error si ocurre
						
				}
				
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();		//Muestra el error si ocurre
			
		}
		

		t_fin = System.currentTimeMillis();
		long t_total = t_fin - t_comienzo;				// Se termina de contar el tiempo de ejecucion
	
		
		System.out.println("El archivo " + nombre + " tiene " + contador + " caracteres, su tiempo de ejcución ha sido: " + t_total);		// Devuelve la cantidad de caracteres que tiene el documento
		
	}
	
}
