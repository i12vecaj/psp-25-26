package main;


public class CuentaCorriente{

	double saldo; // variable donde se guarda el saldo

	public CuentaCorriente(double saldo) {				

		this.saldo = saldo;  // Constructor
	}
	
	public double getSaldo() { 
		
		return saldo;      // Getter de saldo
		
	}

	public void setSaldo(double saldo) {
		
		this.saldo = saldo;		//Setter de saldo
		
	}
	
	public synchronized void ingresar(double ingreso, String persona) {    // Metodo principal
		
		try {					// Control de errores
			
			Thread.sleep(250 / 2500);			// Sleep para que "duerma" durante un cierto tiempo
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();			// Muestra el error si ocurre
			
		}
		
		double nuevoSaldo = ingreso + saldo;  // Variable en la que se muestra el saldo tras el ingreso
		
		System.out.println(persona + " ha ingresado " + ingreso + "$ a su cuenta, su saldo antes era  " + saldo + "$, su saldo actual es: " + nuevoSaldo + "$.");
		
		saldo = nuevoSaldo;  // Se cambia el valor del saldo al actual tras el ingreso
		
	}
	
}
