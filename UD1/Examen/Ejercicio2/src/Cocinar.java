

public class Cocinar implements Runnable {
    private int id;
    private int iter;

    public Cocinar (int id, int iter)
    {
        this.id = id;
        this.iter = iter;
    }

    public void run()
    {
        System.out.println("[Cocinar] Comenzando tarea...");
        for (int i = 0; i < iter; i++)
        {
//            System.out.println("Iteración " + i + " dentro del hilo " + id);
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("[Cocinar] Tarea finalizada.");
    }
}