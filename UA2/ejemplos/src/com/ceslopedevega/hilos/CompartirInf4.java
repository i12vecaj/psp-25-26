package com.ceslopedevega.hilos;

class Cuenta2 {
	private int saldo ;
	Cuenta2 (int s) { saldo = s; }
	
	int getSaldo() { return saldo; }
	void restar(int cantidad) { saldo=saldo-cantidad; }
	
	synchronized void RetirarDinero(int cant, String nom) {
		if (getSaldo() >= cant) {
			System.out.println(nom+": SE VA A RETIRAR SALDO (ACTUAL ES: "+getSaldo()+ ")" );
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {	}
			
			restar(cant);		
			
			System.out.println("\t"+ nom+ " retira =>"+cant + " ACTUAL("+getSaldo()+")" );	
		} else {
			System.out.println(nom+ " No puede retirar dinero, NO HAY SALDO("+getSaldo()+")" );
		}
		if (getSaldo() < 0) {
			System.out.println("SALDO NEGATIVO => "+getSaldo());
		}
		
	}//retirar
}

class SacarDinero2 extends Thread {
	private Cuenta2 c;
	String nom;
	public SacarDinero2(String n, Cuenta2 c) {		
		super(n);
		this.c = c;
	}
	public  void run() {		
		for (int x = 1; x<= 4; x++) {		
			c.RetirarDinero(10, getName());			
		}		
	}// run
	
}
public class CompartirInf4 {
	public static void main(String[] fer) {
		Cuenta2 c = new Cuenta2(40);
		SacarDinero2 h1 = new SacarDinero2("Ana", c);
		SacarDinero2 h2 = new SacarDinero2("Juan", c);

		h1.start();
		h2.start();
	}
}