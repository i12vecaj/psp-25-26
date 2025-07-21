package com.ceslopedevega.hilos;

public class UsaPrimerHiloR {
	public static void main(String[] args) {
		//Primer hilo
		PrimerHiloR	hilo1 = new PrimerHiloR();
		new Thread(hilo1).start();

		//Segundo hilo
		PrimerHiloR hilo2 = new PrimerHiloR();
		Thread hilo = new Thread(hilo2);
		hilo.start(); 
		
		//Tercer Hilo
		new Thread(new PrimerHiloR()).start();
	}
}//UsaPrimerHiloR
