package main;

public class Main {

	public static void main(String[] args) {
		
		Thread t1 = new Thread(new ModuloTrabajo(1, "Modulo A"));
		Thread t2 = new Thread(new ModuloTrabajo(2, "Modulo B"));
		Thread t3 = new Thread(new ModuloTrabajo(3, "Modulo C"));

		try {
			
			t1.setPriority(8); // Como se espera a que termine el hilo el setpriority no afecta.
			t1.start();
			t1.join();
			
		} catch (InterruptedException e) {

			e.printStackTrace();
			
		}
		
		try {
			
			t2.setPriority(2);
			t2.start();
			t2.join();
			
			t2.interrupt(); // Se interrumpe al estar dormido
			
		} catch (InterruptedException e) {

			e.printStackTrace();
			
		}
		
		try {
			
			t3.setPriority(3);
			t3.start();
			t3.join();
			
		} catch (InterruptedException e) {

			e.printStackTrace();
			
		}
		
	}	
	
}
