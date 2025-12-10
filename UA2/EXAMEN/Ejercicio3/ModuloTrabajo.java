package main;

public class ModuloTrabajo extends Thread{

	int id;
	String nombre;
	

	public ModuloTrabajo(int id, String nombre) {

		this.id = id;
		this.nombre = nombre;
		
	}
	
	public synchronized void run() {
		
		for (int i = 0; i < 6; i++) {
		
		
			try {
				
				sleep(500);
				System.out.println(nombre + " - Iteracion " + i);
				
				if (i == 3) {
					
					Thread.yield(); // Trata de ceder el uso a otro hilo, pero como los otros hilos esperan a que termine de usarse no afecta.
					
				}
				
				if(i == 5) {
					
					System.out.println("El hilo " + nombre + " estado vivo: " + isAlive() + "\n");
					toString();
					
				}
				
				if (nombre == "Modulo B") {
					
					sleep (15);
					
				}
				
			} catch (Exception e) {
	
				interrupt();
				System.out.println(nombre + " interrumpido durante la ejecución.");
				
			}
		
		}
		
	}

	@Override
	public String toString() {
		
		return "ModuloTrabajo {nombre= '" + nombre + "', id = " + id + ", prioridad = ";
		
	}

	public long getId() {
		
		return id;
		
	}

	public String getNombre() {
		
		return nombre;
		
	}
	
}
