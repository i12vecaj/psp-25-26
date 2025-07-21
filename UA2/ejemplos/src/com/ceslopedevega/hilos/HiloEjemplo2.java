package com.ceslopedevega.hilos;

public class HiloEjemplo2 extends Thread {
	
  public void run() {
     System.out.println(
  	   "Dentro del Hilo  : " + Thread.currentThread().getName() + 
 	   "\n\tPrioridad    : " + Thread.currentThread().getPriority() + 
       "\n\tID           : " + Thread.currentThread().getId() +	  	  
       "\n\tHilos activos: " + Thread.currentThread().activeCount());
  }
  //
  public static void main(String[] args) {
	 
	Thread.currentThread().setName("Principal");
	System.out.println(Thread.currentThread().getName());
	System.out.println(Thread.currentThread().toString());		
	  
	
	HiloEjemplo2 h = null;	
	
	for (int i = 0; i < 3; i++) {
	  h = new HiloEjemplo2(); //crear hilo
	  h.setName("HILO"+i);    //damos nombre al hilo
	  h.setPriority(i+1);     //damos prioridad
	  h.start();              //iniciar hilo	  
	  
	  System.out.println(
		"Informacion del " + h.getName() + ": "+ h.toString());	  
      }
	System.out.println("3 HILOS CREADOS...");	
	System.out.println("Hilos activos: " +Thread.activeCount());	
  }//
  
}// HiloEjemplo2
