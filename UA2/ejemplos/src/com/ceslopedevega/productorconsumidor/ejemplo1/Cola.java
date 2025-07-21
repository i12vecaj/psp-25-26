package com.ceslopedevega.productorconsumidor.ejemplo1;

public class Cola {
    private int numero;
    private boolean disponible = false;//inicialmente cola vacia

    public int get() {
	    if(disponible) {      //hay número en la cola
		disponible = false; //se pone cola vacía
            return numero;      //se devuelve
	    }
          return -1;	//no hay número disponible, cola vacía	
    }

    public void put(int valor) {
        numero = valor;    //coloca valor en la cola 
        disponible = true; //disponible para consumir, cola llena	
    }
}
