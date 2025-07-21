package com.ceslopedevega.hilos;

class ObjetoCompartido {	
	public void PintaCadena (String s) {
		System.out.print(s);
	}
  }// ObjetoCompartido

class HiloCadena extends Thread {
	private ObjetoCompartido objeto;
    String cad;
	public HiloCadena (ObjetoCompartido c, String s) {		
		this.objeto = c;
		this.cad=s;
	}
	public void run() {
		  synchronized (objeto) {
		     for (int j = 0; j < 10; j++) {				
				 objeto.PintaCadena(cad);
				 objeto.notify(); //aviso que ya he usado el objeto
				 try {
					objeto.wait();//esperar a que llegue un notify 
				 } catch (InterruptedException e) {
					e.printStackTrace();
				 }			
		     }//for 
		     objeto.notify(); //despertar a todos los wait sobre el objeto
		  }//fin bloque synchronized

		  System.out.print("\n"+cad + " finalizado");
		}//run

}//HiloCadena

public class BloqueoHilos {
	public static void main(String[] args) {
		ObjetoCompartido com = new ObjetoCompartido();
		HiloCadena  a = new HiloCadena (com, " A ");
		HiloCadena  b = new HiloCadena (com, " B ");
		a.start();
		b.start();
	}
}//BloqueoHilos

