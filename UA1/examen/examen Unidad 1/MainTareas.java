/*
 * Nombre: Antonio Rodríguez Cortés
 * Fecha: 29/10/2025
 * Descripción: Ejercicio de tareas concurrentes en Java utilizando hilos.
 * FR implementados: FR1, FR2
 */

public class MainTareas {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hilo principal id (antes): " + Thread.currentThread().getId());

        // Creamos los tres hilos
        Thread t1 = new Thread(new tarea("Tarea 1"));
        Thread t2 = new Thread(new tarea("Tarea 2"));
        Thread t3 = new Thread(new tarea("Tarea 3"));

        // Iniciamos los hilos
        t1.start();
        t2.start();
        t3.start();

        // Esperamos a que terminen
        t1.join();
        t2.join();
        t3.join();

        System.out.println("Hilo principal id (después): " + Thread.currentThread().getId());
    }
}

/*
                * Qué diferencia hay entre crear un proceso y crear un hilo.
 
 * La diferencia clave es que un proceso se realiza un codigo secuencial y al crear un
 * hilo se permite la ejecución concurrente de múltiples tareas dentro del mismo proceso. 
*/
