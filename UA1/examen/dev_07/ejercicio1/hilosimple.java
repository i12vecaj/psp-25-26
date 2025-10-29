
public class hilosimple implements Runnable {
	private int id;
	private int iter;
	
	public hilosimple (int id, int iter)
	{
		this.id = id;
		this.iter = iter;
	}
	
	public void run()
	{

			System.out.println("Iteracion " + iter + " dentro del hilo " + id);
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		System.out.println("El hilo " + id + " ha terminado su ejecucion");
	}
}