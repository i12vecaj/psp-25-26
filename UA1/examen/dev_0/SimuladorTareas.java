/*
 * Nombre: German Gutierrez Gimenez
 * Fecha: 29/10/2025
 * Descripción: Main
 * FR implementados: [FR1, FR2...]
 */

public class Main {
	public static void main(String arg[]) {
		
	        Tarea1 t1 = new Tarea1(5,6);

	        Thread t11 = new Thread(new Tarea1(Tarea1, "Tarea 1"));
	        Thread t2 = new Thread(new Tarea2(Tarea2, "Tarea 2"));
	        Thread t3 = new Thread(new Tarea2(Tarea3, "Tarea 2"));

	        t11.start();
	        t2.start();
	    }
		
	}




 /*
 * Nombre: German Gutierrez Gimenez
 * Fecha: 29/10/2025
 * Descripción: Clase Interna
 * FR implementados: [FR1, FR2...]
 */

public class ClaseInterna implements Runnable {
	private int numero;
	private int iter;
	
	public ClaseInterna (int numero, int iter)
	{
		this.numero = numero;
		this.iter = iter;
	}
	
	public void run()
	{
		for (int i = 0; i < iter; i++)
		{
			System.out.println("Esto.. " + i + " dentro del hilo " + numero);
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

}

}




 /*
 * Nombre: German Gutierrez Gimenez
 * Fecha: 29/10/2025
 * Descripción: Tarea1
 * FR implementados: [FR1, FR2...]
 */

/*Crear un proceso es un trabajo secuencial, que hasta que no termina uno, no empieza el otro punto a trabajar
 * 
 * Crear un hilo es diferente a crear un proceso, ya que en este caso no es secuencial, sino que estan trabajando a la vez.
 */


public class Tarea1 implements Runnable {
	private int numero;
	private int iter;
	
	public Tarea1 (int numero, int iter)
	{
		this.numero = numero;
		this.iter = iter;
	}
	
	public Tarea1(double d) {
		// TODO Auto-generated constructor stub
	}

	public void run()
	{
		for (int i = 0; i < iter; i++)
		{
			System.out.println("Esto.. " + i + " dentro del hilo " + numero);
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

}

	public static void start() {
		// TODO Auto-generated method stub
		
	}
}




 /*
 * Nombre: German Gutierrez Gimenez
 * Fecha: 29/10/2025
 * Descripción: Tarea2
 * FR implementados: [FR1, FR2...]
 */

public class Tarea2 implements Runnable {
	private int numero;
	private int iter;
	
	public Tarea2 (int numero, int iter)
	{
		this.numero = numero;
		this.iter = iter;
	}
	
	public Tarea2(double d) {
		// TODO Auto-generated constructor stub
	}

	public void run()
	{
		for (int i = 0; i < iter; i++)
		{
			System.out.println("Esto.. " + i + " dentro del hilo " + numero);
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

}


	public static void start() {
		// TODO Auto-generated method stub
		
	}

}



 /*
 * Nombre: German Gutierrez Gimenez
 * Fecha: 29/10/2025
 * Descripción: Tarea3
 * FR implementados: [FR1, FR2...]
 */


public class Tarea3 implements Runnable {
	private int numero;
	private int iter;
	
	public Tarea3 (int numero, int iter)
	{
		this.numero = numero;
		this.iter = iter;
	}
	
	public Tarea3(double d) {
		// TODO Auto-generated constructor stub
	}

	public void run()
	{
		for (int i = 0; i < iter; i++)
		{
			System.out.println("Esto.. " + i + " dentro del hilo " + numero);
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

}

	public static void start() {
		// TODO Auto-generated method stub
		
	}


}


