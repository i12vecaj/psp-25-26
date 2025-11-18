package tarea2procesos;

public class CuentaCorriente {

	private double saldo;
	private String nombre;

	public CuentaCorriente(int saldo, String nombre) {
		super();
		this.saldo = saldo;
		this.nombre = nombre;
	}

	public double getSaldo() {
		int tiempoEspera = (int)(Math.random() * 2500);
		try {
			Thread.sleep(tiempoEspera);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saldo;
	}

	public void setSaldo(int saldo) {
		int tiempoEspera = (int)(Math.random() * 2500);
		try {
			Thread.sleep(tiempoEspera);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.saldo = saldo;
	}
	public synchronized void incrementarsaldo(double cantidad) {
		System.out.println("La catidad de saldo es: "+saldo+ "Está realizando el ingreso: "+ nombre);
		saldo = saldo + cantidad;
		System.out.println("Saldo actual: "+saldo);
	}
	
	
}
