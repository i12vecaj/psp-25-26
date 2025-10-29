/*
* Nombre: Jose Antonio Roda Donoso
* Fecha: 29/10/2025
* Descripción: Clase que representa la tarea de lavar la ropa
* FR implementados: FR1, FR2, FR3
*/
public class LavarRopa implements Runnable {
    @Override
    public void run() {
        System.out.println("Lavar ropa comenzando tarea...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Lavar ropa interrumpida.");
        }
        System.out.println("Lavar ropa tarea finalizada.");
    }
}
