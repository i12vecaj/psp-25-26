/*
 * Nombre: Antonio Rodríguez Cortés
 * Fecha: 29/10/2025
 * Descripción: Ejercicio de tareas concurrentes en Java utilizando hilos.
 * FR implementados: FR1, FR2
 */

public class MainTareasHogar {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Empezando...");

        // Creamos los tres hilos
        Thread t1 = new Thread(new tarea("Cocinar"));
        Thread t2 = new Thread(new tarea("Limpiar"));
        Thread t3 = new Thread(new tarea("LavarRopa"));

        // Iniciamos los hilos
        t1.start();
        t2.start();
        t3.start();

        // Esperamos a que terminen
        t1.join();
        t2.join();
        t3.join();

        System.out.println("terminando...");
    }
}
