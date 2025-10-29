 /*
 * Nombre: David Peláez Pérez
 * Fecha: 29/10/2025
 * Descripción: Crear 3 hilos los cuales tiene distintos tiempos de finalización y mostrar cuando se inicia y cuando termina el hilo 
 * y ver tambíen como el programa es concurrente el orden de inicializado cambia
 * FR implementados: [FR1, FR2, FR3, FR4, FR5]
 */

public class Cocinar implements Runnable {
    
    @Override
    public void run() {
        System.out.println("[Cocinar] Comenzando Tarea ............");
         try 
			{
				Thread.sleep(2000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
        System.out.println("[Cocinar] Tarea Finalizada");
        
    }
}
