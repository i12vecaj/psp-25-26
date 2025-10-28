package main;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

    	Scanner scan = new Scanner(System.in);
    	
    	System.out.print("Escriba: ");
    	String linea = scan.nextLine();
    	
    	int salida;
    	
    	if (linea.length() < 1) {				// Si la longitud es menor que 1 es que no se ha escrito nada
    		
        	System.out.println("\nNo ha escrito nada.");
        	
        	salida = 1;
        	System.exit(salida);
    		
    	} 
    	
    	try {
    	
	    	int numero = Integer.parseInt(linea);				// Pasa el valor de linea a numero entero
	    	
	    	if (numero < 0){
	    		
	        	System.out.println("\nNumero negativo.");		// Si es menor que 0 es negativo		
	        	salida = 3;
	        	
	    		
	    	} else {
	    		
	    		System.out.println("\nNúmero válido.");		// Si no es menor que 0 es positivo
	            salida = 0;
	    		
	    	}
    	
    	} catch (NumberFormatException e) {
    		
    	     System.out.println("\nHa introducido una cadena de texto.");     // Si no puede pasar el valor de linea a numero entero es que es una cadena de texto
             salida = 2;
    		
    	}
    	
    	System.exit(salida);
    	
    }
    
}