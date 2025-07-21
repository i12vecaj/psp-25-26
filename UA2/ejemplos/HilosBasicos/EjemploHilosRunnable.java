/*

Modificaciones propuestas:  

  - Cambiad el número de hilos a crear ... ver qué pasa
  - Comentad el bucle for completo del join() ... ver qué pasa
  - Meted el join() en el mismo bucle for que el start() ... ver qué pasa
  - Y ahora a pensar: ¿Cuándo tiene sentido ejecutar hilos con la interfaz runnable ?
  
*/

import java.util.ArrayList;

class HiloRunnable implements Runnable 
{
	public void run() 
	{
		System.out.println("Hola desde el Hilo! " + Thread.currentThread().getId());
	}
}

public class EjemploHilosRunnable 
{
    public static void main(String args[]) 
    {
        ArrayList<Thread> myThreads = new ArrayList<Thread>(); 
        int threadsNumber = 20;

        for(int i=0; i<threadsNumber; i++)
        {
            HiloRunnable hilo = new HiloRunnable();
            myThreads.add(new Thread(hilo));
        }

        for(int i=0; i<myThreads.size(); i++)
        {
            myThreads.get(i).start();
        }
        
        for(int i=0; i<myThreads.size(); i++)
        {
            try
            {
                myThreads.get(i).join();
            }
            catch (InterruptedException e)
            {
                // Nothing to do here ...
            }
        }
        
        System.out.println("Han terminado los hilos ?");
    }
}
