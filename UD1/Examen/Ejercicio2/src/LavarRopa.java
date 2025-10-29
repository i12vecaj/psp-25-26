
public class LavarRopa implements Runnable {
    private int id;
    private int iter;

    public LavarRopa (int id, int iter)
    {
        this.id = id;
        this.iter = iter;
    }

    public void run()
    {

        System.out.println("[Lavar ropa] Comenzando tarea...");
        for (int i = 0; i < iter; i++)
        {
//            System.out.println("Iteración " + i + " dentro del hilo " + id);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("[Lavar ropa] Tarea finalizada.");
    }
}