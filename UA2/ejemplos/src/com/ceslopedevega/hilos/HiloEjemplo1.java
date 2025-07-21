package com.ceslopedevega.hilos;

public class HiloEjemplo1 extends Thread {
	// constructor
	public HiloEjemplo1(String nombre) {
		super(nombre);
		System.out.println("CREANDO HILO:" + getName());
	}

	// metodo run
	public void run() {
		for (int i=0; i<5; i++) 
			System.out.println("Hilo:" + getName() + " C = " + i);
	}

	//
	public static void main(String[] args) {
		HiloEjemplo1 h1 = new HiloEjemplo1("Hilo 1");
		HiloEjemplo1 h2 = new HiloEjemplo1("Hilo 2");
		HiloEjemplo1 h3 = new HiloEjemplo1("Hilo 3");
			
		h1.start();
		h2.start();
		h3.start();
		
		System.out.println("3 HILOS INICIADOS...");
	}// main
	
}// HiloEjemplo1
