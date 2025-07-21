package com.ceslopedevega.productorconsumidor.ejemplo3;

public class ProductoConsumidor {
  public static void main(String[] args) {  
    Cola cola = new Cola();
	
    Productor p = new Productor(cola, 1);	
	Consumidor c = new Consumidor(cola, 1);
	
    p.start();
	c.start();

	
  }
}