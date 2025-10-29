package ejercicio3;

public class hilolavar implements Runnable {
	private int id;
	
	public hilolavar (int id)
	{
		this.id = id;
	}
	
	public void run()
	{

			System.out.println("Comenzando tarea de lavar ");
			try
			{
				Thread.sleep(2000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}

		System.out.println("Lavar ha terminado su ejecucion");
	}
}
