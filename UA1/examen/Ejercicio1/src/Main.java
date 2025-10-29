/*
 * Nombre: Elena Expósito Lara
 * Fecha: 29/10/2025
 * Descripción: [Breve descripción del ejercicio]
 * FR implementados: [FR1, FR2...]
 */

public class Main {

    public static void main(String[] args) {
        // Creo las tres tareas
        SimuladorTareas.Tarea tarea1 = new SimuladorTareas.Tarea("Tarea 1");
        SimuladorTareas.Tarea tarea2 = new SimuladorTareas.Tarea("Tarea 2");
        SimuladorTareas.Tarea tarea3 = new SimuladorTareas.Tarea("Tarea 3");

        // Creo los hilos correspondientes
        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);

        // Inicio los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // Espero a que terminen todas las tareas
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido mientras esperaba.");
        }
    }
}
