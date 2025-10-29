package examenprocesos;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Nombre: [Samuel Calero Ruiz]
		 * Fecha: [29/10/2025]
		 * Descripción: [Simulador de tareas]
		 * FR implementados: []
		 */
			Tarea tarea1 = new Tarea("Tarea1");
			Tarea tarea2 = new Tarea("Tarea2");
			Tarea tarea3 = new Tarea("Tarea3");
			
			Thread t1 = new Thread(tarea1);
			Thread t2 = new Thread(tarea2);
			Thread t3 = new Thread(tarea3);
			
			t1.start();
			t2.start();
			t3.start();
	}

}
