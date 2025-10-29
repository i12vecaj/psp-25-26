package ejercicio3;

public class hilolimpiar implements Runnable {
	private int id;

	
	public hilolimpiar (int id)
	{
		this.id = id;
	}
	
	public void run()
	{

			System.out.println("Comenzando tarea de limpiar ");
			try
			{
				Thread.sleep(1500);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}

		System.out.println("Limpiar ha terminado su ejecucion");
	}
}
