/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 29/10/2025
 * Descripcion: Clase que representa una tarea que se ejecuta en un hilo.
 * Muestra mensajes al empezar y terminar, con una pausa aleatoria.
 * FR Implementados: FR1, FR3
 */
public class Tarea implements Runnable {
    private String nombre;

    public Tarea(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        long idHilo = Thread.currentThread().getId();
        System.out.println( nombre + " ejecutándose en el hilo " + idHilo);

        try {
            int tiempo = (int) (Math.random() * 1000 + 500);
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            System.out.println(nombre + " interrumpida.");
        }

        System.out.println(nombre + " finalizada.");
    }
}
