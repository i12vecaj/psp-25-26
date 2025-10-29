/*
Nombre: Daniel Santaflorentina Picchi
Fecha: 29/10/2025
Descripción: Archivo Main para ejecutar tareas
 */

public class Main {
    public static void main(String[] args) {

        Thread tarea1 = new Thread(new Tarea("Tarea 1", 2));
        Thread tarea2 = new Thread(new Tarea("Tarea 2", 4));
        Thread tarea3 = new Thread(new Tarea("Tarea 3", 6));

        tarea1.start();
        tarea2.start();
        tarea3.start();
    }
}

/*
Diferencia entre crear un proceso y crear un hilo:

    Un proceso es la instancia de la ejecución de un programa, el proceso suele ser lento y pesado a nivel máquina, por
    contrario un hilo es una parte de un proceso, y un proceso puede tener muchos hilos, los cuales comparten su memoria
    y consumen menos recursos, por lo que, por norma general, un hilo es más rápido y ligero de ejecutar que los
    procesos, ya que al compartir datos y memoria no tienen que tener tanta espera para ejecutarse.

Ventajas e inconvenientes de la programación concurrente:

    La programación concurrente permite ejecutar varios programas de manera simultánea (no necesariamente a la misma vez)
    por lo que si hay muchas programas que ejecutar, nos adelanta tiempo y es más eficaz.
 */