package Ex3;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		ModuloTrabajo m1 = new ModuloTrabajo("Modulo 1");
		ModuloTrabajo m2 = new ModuloTrabajo("Modulo 2");
		ModuloTrabajo m3 = new ModuloTrabajo("Modulo 3");
		
		m1.setPriority(1);
		m2.setPriority(5);
		m3.setPriority(10);
		
		m1.start();
		m2.start();
		m3.start();
		
		System.out.println("Hilo 1 está activo: "+m1.isAlive());
		System.out.println("Hilo 2 está activo: "+m2.isAlive());
		System.out.println("Hilo 3 está activo: "+m3.isAlive());
		
		m2.interrupt();
		
		m1.join();
		m2.join();
		m3.join();
		System.out.println("Módulo<"+m1.getNombre()+"> finalizado, estado vivo: <"+m1.isAlive()+">");
		System.out.println("Módulo<"+m2.getNombre()+"> finalizado, estado vivo: <"+m2.isAlive()+">");
		System.out.println("Módulo<"+m3.getNombre()+"> finalizado, estado vivo: <"+m3.isAlive()+">");
		
		System.out.println(m1.toString());
		System.out.println(m2.toString());
		System.out.println(m3.toString());
	}


}
/* OBSERVACIONES FINALES: sobre las prioridades de hilos, se ajusta la prioridad manualmente pero debido al tema de que eso tambien depende de la carga del sistema y de los hilos
 * a la hora de ejecutarse no siguen al 100% la prioridad, sobre yield no se si lo he implementado mal pero no he visto que haga lo que tiene que hacer, que es por asi decirlo
 * dejar paso al siguiente hilo, y por ultimo la interrupcion ocurre y se ve en la consola, aunque luego el hilo sigue aunque haya usado interrupt, pienso que esto es por el join
 * pero no estoy seguro.
 * */
 