import java.util.Random;

/*
Nombre: Daniel Santaflorentina Picchi
Fecha: 29/10/2025
Descripción: Archivo Tarea que implementa Runnable para correr tareas
 */

public class Tarea implements Runnable {

    private Random rm = new Random();

    String nombre;
    int hilo;
    public Tarea(String nombre, int hilo) {
        this.hilo = hilo;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println(nombre + " ejecutándose en el hilo " + hilo);
        try {
            Thread.sleep(rm.nextInt(1500)+500);
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println(nombre + " finalizada");

    }
}