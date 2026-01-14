package main;

import java.net.*;
import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	String URL = "";
    	
        try {
        	
        	while (!URL.equals("localhost")) {
   	        	       		
	            System.out.print("Escribe una url: ");
	        	URL = scan.nextLine();
	        		
	            InetAddress host = InetAddress.getByName(URL);
	            System.out.println("Nombre:  " + host.getHostName());
	            System.out.println("IP: " + host.getHostAddress());
            
	            System.out.println("");
	            
        	}
            
        } catch (Exception e) {
        	
            e.printStackTrace();
            
        }
        
    }
    
}