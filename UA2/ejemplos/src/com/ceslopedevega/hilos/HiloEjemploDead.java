package com.ceslopedevega.hilos;

public class HiloEjemploDead extends Thread {
  private boolean stopHilo= false; 
  public void pararHilo()  {
    stopHilo = true;
  }  
  //metodo run
  public void run() {
	while (!stopHilo) {
	  System.out.println("En el Hilo");
	}
  }
  public static void main(String[] args) {
	HiloEjemploDead h = new HiloEjemploDead ();
    h.start();
	for(int i=0;i<100000; i++) ;//no hago nada	
	
	h.pararHilo();
  }// main   
 }//fin clase hilo
