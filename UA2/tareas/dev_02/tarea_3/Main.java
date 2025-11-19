package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
	
//---------------------------------------------------------------------------------------------------------- FR1:
		
		FileReader fr;				// Lector de archivos para leer el documento
		
		int caracteres = 0;			// Variable para pasar por los caracteres del documento
		
		int contador = 0; 		// Contador de caracteres
		
		long t_comienzo, t_fin;				
		t_comienzo = System.currentTimeMillis();		// Se empieza a contar el tiempo de ejecucion
		
		
		try {			// Estructura de control
			
			fr = new FileReader("El Quijote.txt"); // Nombre del documento a leer
			
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
	
		
//---------------------------------------------------------------------------------------------------------- FR2:
		
		
		System.out.println("El archivo El Quijote.txt tiene " + contador + " caracteres, su tiempo de ejcución ha sido: " + t_total);		// Devuelve la cantidad de caracteres que tiene el documento
		
		Thread archivo1 = new Thread(new Contador("El Quijote Modificado.txt"));			// Se les pasa el nombre de los arhcivos
		Thread archivo2 = new Thread(new Contador("El Quijote Aun Más Modificado.txt"));


		archivo1.start();			// Inicia los hilos
		archivo2.start();
		
	}
	
}


//----------------------------------------------------------------------------------------------------------- FR3:
// El tiempo de ejecucion del documento del FR1 es entorno al doble del tiempo de ejecucion de los documentos del FR2

