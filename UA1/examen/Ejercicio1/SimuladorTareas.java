/*
 * Nombre: David Peláez Pérez
 * Fecha: 29/20/2025
 * Descripción: [Crear un interfaz para que se puedan crear hilos con nombre y que a la hora de ejectarse muestren su nombre en que hilo estan 
 * y a la hora de terminar muestren que han finalizado]
 * FR implementados: [FR1, FR2...]
 */

public class SimuladorTareas {
    public static void main(String[] args) {
        Tarea tarea1 = new Tarea("Tarea1");
        Tarea tarea2 = new Tarea("Tarea2");
        Tarea tarea3 = new Tarea("Tarea3");

        Thread t1 = new Thread(tarea1);
        Thread t2 = new Thread(tarea2);
        Thread t3 = new Thread(tarea3);

        t1.start();
        t2.start();
        t3.start();




        //La diferencia entre un proceso y un hilo es que un proceso es un programa en ejecución el cual tiene su propio PID Y PCB .El proceso puede 
        //compartir los recursos o competir por ellos mientras que los hilos comparten esos recursos del proceso porque se crean dentro y trabajan juntos
        //tambíen en java los procesos se crean con la Clase ProcessBuilder y los hilo se pueden crear extendiendo de la clase Thread o implementando una interfaz Runnable

        //Beneficios de la programación Concurrente se mejora el aprovechamiento de la CPU e incremento de la velocidad de ejecución
        //Inconvenientes de la programación concurrente varios procesoso pueden acceder a la misma variable y mientras que uno esta modificandola otro la esta leyendo
    }
}