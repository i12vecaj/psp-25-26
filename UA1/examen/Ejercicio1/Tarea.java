/*
 * Nombre: David Peláez Pérez
 * Fecha: 29/20/2025
 * Descripción: [Crear un interfaz para que se puedan crear hilos con nombre y que a la hora de ejectarse muestren su nombre en que hilo estan 
 * y a la hora de terminar muestren que han finalizado]
 * FR implementados: [FR1, FR2...]
 */
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
