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

public class LavarRopa implements Runnable {
    @Override
    public void run() {
        System.out.println("[Lavar ropa] Comenzando tarea...");
        try {
            Thread.sleep(3000); // 3 segundos simulando que tarda más
        } catch (InterruptedException e) {
            System.out.println("[Lavar ropa] Tarea interrumpida :(");
        }
        System.out.println("[Lavar ropa] Tarea finalizada.");
    }
}
