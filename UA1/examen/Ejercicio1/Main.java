 /*
  * Nombre: Eduardo Ruz López
  * Fecha: 29/10/2025
  * Descripción: Creacion de un simulador de tareas que ejecuta varias tareas a la vez
  * FR implementados: a,b,c,d,e,f
  */


public class Main {
    public static void main(String[] args) {
        SimuladorTareas tarea1 = new SimuladorTareas(1,"Tarea1");
        SimuladorTareas tarea2 = new SimuladorTareas(2,"Tarea2");
        SimuladorTareas tarea3 = new SimuladorTareas(3,"Tarea3");

        Thread t1 = new Thread(tarea1);
        Thread t2 = new Thread(tarea2);
        Thread t3 = new Thread(tarea3);

        t1.start();
        t2.start();
        t3.start();
    }
}

/*
Que diferencia hay entre crear un proceso y un hilo?
Cuando creamos varios procesos, uno no se va a ejecutar si el otro no ha terminado. Sin embargo si
lo hacemos con hilos se van a ejecutar a la vez sin necesidad de esperar a que acabe el tiempo de ejecuccion
del anterior.

Que ventajas e inconvenientes tiene la programacion concurrente
Con la programacion concurrente podemos ejecutar varios procesos que competiran y colaboraran por los recursos
de la cpu sin necesidad de que un proceso tenga que esperar a que acabe otro para ejecutarse,
lo que incrementa la velocidad de ejecucion y se aprovecha mejor la cpu.
El inconveniente principal es cuando varios procesos intentan acceder a la misma variable o recurso,
lo que puede provocar exclusiones.
 */
