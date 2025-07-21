package com.ceslopedevega.hilos;
//primera aproximacion del bloqueo de hilos

class ObjetoCompartido1 {	
	public void PintaCadena (String s) {
		System.out.print(s);
	}
  }// ObjetoCompartido1

class HiloCadena1 extends Thread {
	private ObjetoCompartido1 objeto;
    String cad;
	public HiloCadena1 (ObjetoCompartido1 c, String s) {		
		this.objeto = c;
		this.cad=s;
	}
	public void run() {		
			  synchronized (objeto) {
				  for (int j = 0; j < 10; j++)	
				     objeto.PintaCadena(cad);	
		  }//fin bloque synchronized

		  System.out.print("\n"+cad + " finalizado");
		}//run

}//HiloCadena1

public class BloqueoHilosInicial {
	public static void main(String[] args) {
		ObjetoCompartido1 com = new ObjetoCompartido1();
		HiloCadena1  a = new HiloCadena1 (com, " A ");
		HiloCadena1  b = new HiloCadena1 (com, " B ");
		a.start();
		b.start();
	}
}//BloqueoHilosInicial

