public class ModuloTrabajo extends Thread {
	private final String nombre;

	public ModuloTrabajo(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public void run() {
		System.out.println("Módulo " + nombre + " iniciado ID: " + getId());
		for (int i = 1; i <= 5; i++) {
			System.out.println("Módulo " + nombre + " – iteración " + i);
			
            //EN LA INTERACCIÓN 3, SE AGREGA YIELD
            if (i == 3) {
				Thread.yield();
			}
			try {
			// SLEEP ENTRE 300 Y 800 MILISEGUNDOS
				long pausa = 300 + (long) (Math.random() * 500);
				Thread.sleep(pausa);
			} catch (InterruptedException e) {
				System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
				return;
			}
		}
	}
    
	@Override
	public String toString() {
		return "MóduloTrabajo{" +
				"nombre='" + nombre + '\'' +
				", id=" + getId() +
				", prioridad=" + getPriority() +
				'}';
	}
}
 