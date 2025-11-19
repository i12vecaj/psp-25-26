package main;

import java.io.IOException;

public class main {

public static void main(String[] args) {
	
	Thread c1 = new Thread(new Operacion());  // Se crea un Thread de Operacion
	
	c1.start(); // Se lanza el Thread de Operacion
	
	
	CuentaCorriente cuenta2 = new CuentaCorriente(100);   // Se crea un objeto para de CuentaCorriente sobre la que hacer ingresos

	cuenta2.ingresar(100, "Cuenta 3");
	cuenta2.ingresar(12, "Cuenta 4");					//Se crean los hilos que extienden del objeto
	cuenta2.ingresar(400, "Cuenta 5");

	
}
	
	
}
