

/*
 * Nombre: Rafa Moreno Moreno
 * Fecha: 29/10/2025
 * Descripción: Uso de hilos para crear tareas
 * FR implementados: FR1, FR2
 */

public class SimuladorTareas {
    public static void main (String[] args)
    {
        Tarea hilo1 = new Tarea(1, 10, "Tarea 1");
        Tarea hilo2 = new Tarea(2, 4, "Tarea 2");
        Tarea hilo3 = new Tarea(3, 5, "Tarea 3");

        // Se crean los hilos (no extienden Thread directamente)
        Thread t1 = new Thread(hilo1);
        Thread t2 = new Thread(hilo2);
        Thread t3 = new Thread(hilo3);

        // Se lanzan los hilos
        t1.start();
        t2.start();
        t3.start();
    }
}

/*
La diferencia entre crear un proceso y crear un hilo es que un proceso puede tener varios hilos, es decir el proceso
es lanzado por el procesador y dentro de él se pueden lanzar varios hilos(un hilo para la música de fondo del programa, otro hilo para los subtítulos...),
en cambio el procesador no lanza primero hilos sobre la nada, primero necesita algo en donde almacenar esos hilos, y eso es el proceso

 Programación concurrente:

 Ventajas:

 - Puedes aprovechar mejor la CPU: al poder tener varios hilos corriendo en el mismo procesador aprovechas mejor los recursos ya
 que en el mismo procesador puedes tener varias tareas ejecutándose (hilos) sin tener que recurrir al paralelismo
 - Mayor velocidad de ejecución: como las instrucciones se intercalan ocurren varias tareas al mismo tiempo y puedes llegar a tu objetivo más rápido.
 Por ejemplo el metodo start() al trabajar con hilos, hace que el hilo se vaya ejecutando después llegue otro hilo y también se vaya ejecutando a la vez,
 evitando así que haya tareas esperando a que otras tareas acaben sus iteraciones como pasa con el metodo run()
- Solución de problemas en aplicaciones concurrentes por naturaleza: gracias a la concurrencia las aplicaciones que necesitan que varias tareas ocurran de forma simultánea es posible,
por ejemplo en videojuegos, sin la concurrencia no se podría mover al personaje mientras escuchas música de fondo y mientras hay procesos calculando tu posición en el mapa.

Desventajas:

- Exclusión mutua: ocurre cuando dos procesos quieren modificar la misma variable, por ejemplo dos usuarios diferentes
intentado modificar el mismo campo en una base de datos.

- Condición de sincronización: ocurre cuando un proceso tiene que esperar a otro para poder comenzar.

 */