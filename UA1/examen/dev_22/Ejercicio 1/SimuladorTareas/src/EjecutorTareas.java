/*
* Nombre: Jose Antonio Roda Donoso
* Fecha: 29/10/2025
* Descripcion: Programa principal que lanza tres tareas en hilos distintos.
 * El hilo principal espera a que todas terminen antes de finalizar.
 * FR Implementados: FR2,FR4
*/

public class EjecutorTareas {
    public static void main(String[] args) {
        long idPrincipal = Thread.currentThread().getId();
        System.out.println("Hilo principal iniciado (ID: " + idPrincipal + ")");

        Tarea tarea1 = new Tarea("Tarea 1");
        Tarea tarea2 = new Tarea("Tarea 2");
        Tarea tarea3 = new Tarea("Tarea 3");

        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido.");
        }

        System.out.println("Hilo principal finalizado (ID: " + idPrincipal + ")");
    }
}
/*
* a) Que diferencia hay entre los procesos y los hilos
* Un proceso es un programa que se esta ejecutando en su memoria.
* Los hilos son una parte del proceso que comparte memoria
* b) Que ventajas o incovenientes tiene la programacion concurrente
* Ventaja:
* Podemos hacer varias tareas a la vez
* Incoveniente:
* Es mas dificil de programar y depurar
 */
