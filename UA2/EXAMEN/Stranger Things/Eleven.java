package main;

import java.util.List;

public class Eleven{

	List<Portales> listaPortales;
	
	
	public synchronized void cerraPortal(Portales portal) {
		
		String nombrePortal = portal.getPortal();
		
		listaPortales.add(portal);
		
		System.out.println("Se ha cerrado el portal " + nombrePortal);
		
		notifyAll();
		
		contarPortalesCerrados();
		
	}
	
	public synchronized void esperarPortal() {
				
		while (listaPortales.isEmpty()) {
			
			try {
				
				wait();
				
			} catch (Exception e) {
				
				System.out.println("Error: " + e);
				
			}
		}
		
	}
	
	public int contarPortalesCerrados() {
		
		return listaPortales.size();
		
	}
	
}
