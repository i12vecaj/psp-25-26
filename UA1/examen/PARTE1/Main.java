 /*
  * Nombre: [Rafael Marcos Serrano]
  * Fecha: [29/10/2025]
  * Descripción: [Implementacion de tres tareas mediante tres hilos distintos simulando tiempo de ejecucion]
  * FR implementados: [FR1, FR2...]
  */

public class Main {
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

        /*Qué diferencia hay entre crear un proceso y crear un hilo.
        La diferencia es que cuando creamos un proceso, ejecuta un proceso de nuestro sistema segun los parametros
        que le demos nosotros ,mientras que, un hilo digamos que es una secuencia de procesos o acciones que ocurren
        digamos que en un hilo se pueden ejecutar procesos dentro, pudiendo ejecutar varios procesos en un hilo

        Qué ventajas e inconvenientes tiene la programación concurrente
        Yo diría que las ventajas que tiene serían la posibilidad de ejecutar varias tareas al mismo tiempo, ahorrando
        tiempo de ejecucion al tener que esperar a que otras tareas terminen y se nos abre un abanico de posibilidades
        ya que podriamos hacer varios procesos que dependan de resultados de otros, asi que el nivel de complejidad
        de los programas que podemos hacer con programación concurrente aumenta considerablemente
        Las desventajas evidentemente son un mayor gasto de recursos y añadiendole un nivel de complejidad de
        abstracción a la hora de programar, pero con práctica y experiencia se puede solventar esto

         */

    }
}