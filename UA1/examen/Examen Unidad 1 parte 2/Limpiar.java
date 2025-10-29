/*
 * Nombre: Antonio Rodríguez Cortés
 * Fecha: 29/10/2025
 * Descripción: Tareas de hogar de forma inteligente.
 * FR implementados: FR1, FR2
 */

import java.util.concurrent.ThreadLocalRandom;

public class Limpiar implements Runnable {
    private final String nombre;

    public Limpiar(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {

        System.out.println("[" + nombre + "] comenzando tarea...");
        try {
           
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + nombre + "] interrumpida.");
            return;
        }
        // Mostramos que la tarea ha finalizado
        System.out.println("[" + nombre + "] finalizada.");
    }
}
