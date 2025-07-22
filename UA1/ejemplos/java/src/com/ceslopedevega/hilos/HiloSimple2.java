package com.ceslopedevega.hilos;

public class HiloSimple2 implements Runnable {
	private int id;
	private int iter;
	
	public HiloSimple2 (int id, int iter)
	{
		this.id = id;
		this.iter = iter;
	}
	
	public void run()
	{
		for (int i = 0; i < iter; i++)
		{
			System.out.println("Iteración " + i + " dentro del hilo " + id);
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("El hilo " + id + " ha terminado su ejecución");
	}
}
