public class Tarea implements Runnable {
    public String nombre;

    public Tarea(String nombre) {
        this.nombre = nombre;
    }

    public void run() {
        System.out.println("["+nombre+"] ejecutandose en el hilo "+Thread.activeCount());
        
        try 
			{
				Thread.sleep(1250);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
        System.out.println("["+nombre+"] finalizada");
    }
}
