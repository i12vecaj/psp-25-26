package com.ceslopedevega.hilos;

public class HiloEjemplo1_V2  extends Thread{
	// constructor
	public  HiloEjemplo1_V2(String nombre) {
		super(nombre);
		System.out.println("CREANDO HILO:" + getName());
	}

	// metodo run
	public void run() {
		for (int i=0; i<5; i++) 
			System.out.println("Hilo:" + getName() + " C = " + i);
	}
}// HiloEjemplo1_V2
