package com.ceslopedevega.hilos;

public class UsaHiloEjemplo1_V2 {
	public static void main(String[] args) {
		HiloEjemplo1 h1 = new HiloEjemplo1("Hilo 1");
		HiloEjemplo1 h2 = new HiloEjemplo1("Hilo 2");
		HiloEjemplo1 h3 = new HiloEjemplo1("Hilo 3");
			
		h1.start();
		h2.start();
		h3.start();
		
		System.out.println("3 HILOS INICIADOS...");
	}
}//UsaHiloEjemplo1_V2
