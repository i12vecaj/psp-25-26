/*
 * Nombre: Antonio Rodríguez Cortés
 * Fecha: 29/10/2025
 * Descripción: Ejercicio de tareas concurrentes en Java utilizando hilos.
 * FR implementados: FR1, FR2
 */

import java.util.concurrent.ThreadLocalRandom;

public class tarea implements Runnable {
    private final String nombre;

    public tarea(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        long hiloId = Thread.currentThread().getId();
        // Mostramos el id del hilo
        System.out.println("[" + nombre + "] ejecutándose en el hilo " + hiloId);
        try {
            // Dormir entre 500 y 1500 ms
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + nombre + "] interrumpida.");
            return;
        }
        // Mostramos que la tarea ha finalizado
        System.out.println("[" + nombre + "] finalizada.");
    }
}
