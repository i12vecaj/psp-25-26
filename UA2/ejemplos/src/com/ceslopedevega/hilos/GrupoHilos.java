package com.ceslopedevega.hilos;

public class GrupoHilos implements Runnable {
	public void run() {
		 System.out.println(
					"Informacion del " + Thread.currentThread().getName() + ": "+
							Thread.currentThread().toString());	  
			     
		 for(int i=0;i<1000;i++) {
	         i++;
	      }
	      System.out.println(Thread.currentThread().getName() + 
	      " Finalizando la ejecución.");
	}

	public static void main(String[] args) {

		ThreadGroup grupo = new ThreadGroup("Grupo de hilos");
		GrupoHilos h = new GrupoHilos();

		Thread h1 = new Thread(grupo, h, "Hilo 1");
		Thread h2 = new Thread(grupo, h, "Hilo 2");
		Thread h3 = new Thread(grupo, h, "Hilo 3");

		h1.start();
		h2.start();
		h3.start();

	}

}
