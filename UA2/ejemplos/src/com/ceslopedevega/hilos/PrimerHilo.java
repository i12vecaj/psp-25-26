package com.ceslopedevega.hilos;

public class PrimerHilo extends Thread {
	private int x;

	PrimerHilo(int x) {
		this.x = x;
	}

	public void run() {
		for (int i = 0; i < x; i++)
			System.out.println("En el Hilo... " + i);
	}

	public static void main(String[] args) {
		PrimerHilo p = new PrimerHilo(10);
		p.start();
	}// main

}//PrimerHilo
