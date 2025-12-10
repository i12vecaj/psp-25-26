package main;

public class Laboratorio extends Thread{

	Eleven eleven;
	
	public synchronized void recibirDatos(Eleven eleven) {
		
		this.eleven = eleven;
		
	}
	
	public void run() {
		
		System.out.println("Eleven ha cerrado un portal");
		
	}
	
}
