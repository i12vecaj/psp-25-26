/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 29/10/2025
 * Descripción: Clase que representa la tarea de cocinar
 * FR implementados: FR1, FR2, FR3
 */
public class Cocinar implements Runnable {
    @Override
    public void run() {
        System.out.println("Cocinar comenzando tarea...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Cocinar interrumpida.");
        }
        System.out.println("Cocinar tarea finalizada.");
    }
}

