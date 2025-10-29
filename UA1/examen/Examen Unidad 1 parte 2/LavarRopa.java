/*
 * Nombre: Antonio Rodríguez Cortés
 * Fecha: 29/10/2025
 * Descripción: 
 * FR implementados: FR1, FR2
 */

import java.util.concurrent.ThreadLocalRandom;

public class LavarRopa implements Runnable {
    private final String nombre;

    public LavarRopa(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {

        System.out.println("[" + nombre + "] comenzando tarea...");
        try {
        
            Thread.sleep(ThreadLocalRandom.current().nextInt(2000, 3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + nombre + "] interrumpida.");
            return;
        }
        // Mostramos que la tarea ha finalizado
        System.out.println("[" + nombre + "] finalizada.");
    }
}
