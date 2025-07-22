package com.ceslopedevega.hilos;

public class LanzaHiloSimple2 {
	public static void main (String[] args)
	{
		HiloSimple2 hilo1 = new HiloSimple2(1, 10);
		HiloSimple2 hilo2 = new HiloSimple2(2, 4);
		HiloSimple2 hilo3 = new HiloSimple2(3, 5);
		
		// Se crean los hilos (no extienden Thread directamente)
		Thread t1 = new Thread(hilo1);
		Thread t2 = new Thread(hilo2);
		Thread t3 = new Thread(hilo3);
		
		// Se lanzan los hilos
		t1.start();
		t2.start();
		t3.start();
	}
}
