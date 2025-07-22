package com.ceslopedevega.hilos;

public class LanzaHiloSimple1 {
	public static void main (String[] args)
	{
		HiloSimple1 hilo1 = new HiloSimple1(1, 10);
		HiloSimple1 hilo2 = new HiloSimple1(2, 4);
		HiloSimple1 hilo3 = new HiloSimple1(3, 5);
		
		// Se lanzan los hilos
		hilo1.start();
		hilo2.start();
		hilo3.start();
	}
}
