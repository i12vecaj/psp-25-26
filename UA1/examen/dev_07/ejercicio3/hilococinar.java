package ejercicio3;

public class hilococinar implements Runnable {
	private int id;
	
	public hilococinar (int id)
	{
		this.id = id;
	}
	
	public void run()
	{

			System.out.println("Comenzando tarea cocinar");
			try
			{
				Thread.sleep(3000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}

		System.out.println("Cocinar ha terminado su ejecucion");
	}
}
