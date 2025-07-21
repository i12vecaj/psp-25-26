package com.ceslopedevega.productorconsumidor.ejemplo3;

public class Cola {
    private int numero;
    private boolean disponible = false;//inicialmente cola vacia

    public synchronized int get() {
    	  while (!disponible) {
    	    try {
    	          wait();
    	    } catch (InterruptedException e) { }
    	  }
    	  System.out.println("Se consume: " + numero);    	  
    	  disponible = false;
    	  notify();
    	  return numero;
    	}


    public synchronized void put(int valor) {
    	  while (disponible){
    	    try {
    	          wait();
    	    } catch (InterruptedException e) { }
    	  }
    	  numero = valor;
    	  disponible = true;
    	  System.out.println("Se produce: " + numero);   
    	  notify();
    	}

     
     
}
