package com.ceslopedevega.hilos;

class Contador2 {
	private int c = 0;

	Contador2(int c) {
		this.c = c;
	}

	public void incrementa() {
		c = c + 1;
	}

	public void decrementa() {
		c = c - 1;
	}

	public int getValor() {
		return c;
	}

}// CONTADOR

class HiloA2 extends Thread {
	private Contador2 contador;

	public HiloA2(String n, Contador2 c) {
		setName(n);
		contador = c;
	}

	public void run() {
		synchronized (contador) {
			for (int j = 0; j < 300; j++) {
				contador.incrementa();
				/*try {
					sleep(100);
				} catch (InterruptedException e) {		}
				*/
			}
			System.out.println(getName() + " contador vale "
					+ contador.getValor());
		}

	}
}// HILOA

class HiloB2 extends Thread {
	private Contador2 contador;

	public HiloB2(String n, Contador2 c) {
		setName(n);
		contador = c;
	}

	public void run() {
		synchronized (contador) {
			for (int j = 0; j < 300; j++) {
				contador.decrementa();
				/*try {
					sleep(100);
				} catch (InterruptedException e) {		}
				*/
			}
			System.out.println(getName() + " contador vale "
					+ contador.getValor());
		}
	}
}// HILOB

public class CompartirInf2 {
	public static void main(String[] args) {
		Contador2 cont = new Contador2(100);
		HiloA2 a = new HiloA2("HiloA", cont);
		HiloB2 b = new HiloB2("HiloB", cont);
		a.start();		
		b.start();
		
	}
}