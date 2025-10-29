 /*
  * Nombre: Miguel Castilla Gallego
  * Fecha: 19/10/2025
  * Descripción: [Crear 3 hilos]
  * FR implementados: [FR1]
  */

public class Main {
    public static void main(String[] args) {

        Thread t1 = new Thread(new Tarea("Tarea 1"));
        Thread t2 = new Thread(new Tarea("Tarea 2"));
        Thread t3 = new Thread(new Tarea("Tarea 3"));

        t1.start();
        t2.start();
        t3.start();
    }
}
/*
¿Qué diferencia hay entre crear un proceso y crear un hilo?

    Un proceso es una instancia que ocupa un lugar en memoria y consume unos recursos mientras que los hilos que pertenecen a un proceso comparten lugar y recursos consiguiendo realizar tareas de forma concurrente.

¿Qué ventajas e inconvenientes tiene la programación concurrente?
    Ventajas: Permite realizar múltiples acciones al mismo tiempo mejorando la velocidad de ejecución.
    Inconvenientes: Al fallar uno de los procesos o hilos que forman parte de la programación concurrente pueden fallar los otros, también consume más recursos de la máquina,
 */