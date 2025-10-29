 /*
  * Nombre: Daniel Santaflorentina Picchi
  * Fecha: 29/10/2025
  * Descripción: Metodo Cocinar
  */

import java.util.Random;

public class Cocinar implements Runnable {
    private Random rm = new Random();

    String nombre = "Cocinar";
    @Override
    public void run() {
        System.out.println("[" + nombre + "] " + "Comenzando tarea...");
        try {
            Thread.sleep(rm.nextInt(3000) + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[" + nombre + "]" + "Tarea finalizada");
    }
}
