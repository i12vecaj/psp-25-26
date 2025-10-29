package examenprocesos;

public class main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*
		 * Nombre: [Samuel Calero Ruiz]
		 * Fecha: [29/10/2025]
		 * Descripción: [Simulador de tareas del hogar]
		 * FR implementados: [FR1, FR2, FR3, FR4, FR5]
		 */
		
		System.out.println("Iniciando tareas del hogar...");
		
		cocinar cocina = new cocinar();
		lavarRopa lavar = new lavarRopa();
		limpiar limpieza = new limpiar();
		
		Thread t1 = new Thread(cocina);
		Thread t2 = new Thread(lavar);
		Thread t3 = new Thread(limpieza);
		
		t1.start();
		t2.start();
		t3.start();
		Thread.sleep(4000);
		System.out.println("Las tareas del hogar han sido finalizadas, public void siesta(Mueble sofa) ahora está habilidato");
	}

}
