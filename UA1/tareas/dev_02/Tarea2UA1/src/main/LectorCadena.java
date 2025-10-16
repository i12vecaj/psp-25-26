package main;

import java.util.Scanner;


public class LectorCadena {


    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        StringBuilder entrada = new StringBuilder();

        System.out.println("Introduce caracteres hasta terminar con *: ");

        try {

            while (true) {
            	
                String linea = scanner.nextLine();
                
                if (linea.contains("*")) {

                	// Terminar cuando encuentre un *
                	
                    entrada.append(linea, 0, linea.indexOf('*'));
                    break;
                    
                } else {
                	
                    entrada.append(linea).append("\n");
                    
                }
                
            }

            System.out.println("\nInformación introducida:");
            
            // Muestar por pantalla lo que se haya escrito hasta el *
            
            System.out.println(entrada.toString());          

        } catch (Exception e) {

        	// Control de errores
        	
            System.err.println("Se ha producido un error durante la lectura: " + e.getMessage());
            
        } 
        
    }
    
}
