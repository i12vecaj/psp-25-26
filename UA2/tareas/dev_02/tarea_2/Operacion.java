package main;

public class Operacion extends Thread{

	
	
	@Override
	public void run() {

		CuentaCorriente cuenta1 = new CuentaCorriente(100);
																// Se crea una cuenta para probar que la funcion funciona
		cuenta1.ingresar(200, "Cuenta 2");  
		
	}

	
	
}
