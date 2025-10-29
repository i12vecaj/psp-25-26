/*
 * Nombre: Elena Expósito Lara
 * Fecha: 29/10/2025
 * Descripción:
    * Imagina que en una casa inteligente se realizan varias tareas al mismo tiempo:
    * Lavar la ropa
    * Cocinar
    * Limpiar
    * Cada una de estas tareas debe ejecutarse de forma concurrente, como si cada tarea se hiciera por separado pero al mismo tiempo.
    * Tu objetivo es simular este comportamiento usando hilos en Java.
    * Requisitos funcionales (FR):
    * FR1 (2 puntos):
    * Crear tres clases que implementen Runnable, una para cada tarea: LavarRopa, Cocinar y Limpiar.
    * FR2 (2 puntos):
    * En el método run() de cada clase, mostrar un mensaje al iniciar y otro al finalizar la tarea.
    * Por ejemplo:
    * [Lavar ropa] Comenzando tarea...
    * [Lavar ropa] Tarea finalizada.
    * FR3 (2 puntos):
    * Simular que cada tarea tarda un tiempo distinto en completarse usando Thread.sleep() (por ejemplo, 1, 2 y 3 segundos).
    * FR4 (2 puntos):
    * Desde el método main, crear y arrancar los tres hilos de forma concurrente, mostrando también un mensaje al inicio y al final del programa.
    * FR5 (2 puntos):
    * Comprobar que el orden de ejecución de los mensajes puede variar entre ejecuciones, debido a la concurrencia.
  * FR implementados: FR1, FR2, FR3, FR4, FR5
 */

public class CasaInteligente { // Uso CasaInteligente a modo de método main
    public static void main(String[] args) {
        System.out.println("[Casa Inteligente] Iniciando sistema...");

        // Creo los hilos con sus respectivas tareas
        Thread hiloLavar = new Thread(new LavarRopa());
        Thread hiloCocinar = new Thread(new Cocinar());
        Thread hiloLimpiar = new Thread(new Limpiar());

        // Inicio todos los hilos (con concurrencia)
        hiloLavar.start();
        hiloCocinar.start();
        hiloLimpiar.start();

        // Espero a que todos los hilos terminen antes de finalizar el programa
        try {
            hiloLavar.join();
            hiloCocinar.join();
            hiloLimpiar.join();
        } catch (InterruptedException e) {
            System.out.println("El sistema fue interrumpido.");
        }

        System.out.println("[Casa Inteligente] Todas las tareas han finalizado :D");
    }
}

