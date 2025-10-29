/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 29/10/2025
 * Descripción: Clase que representa la tarea de limpiar
 * FR implementados: FR1, FR2, FR3
 */
public class Limpiar implements Runnable {
    @Override
    public void run() {
        System.out.println("Limpiar comenzando tarea...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Limpiar interrumpida.");
        }
        System.out.println("Limpiar tarea finalizada.");
    }
}

