/*
 * Nombre: [Alejandro Jonás López Serrano]
 * Fecha: [29/10/2025]
 * Descripción: [Programa que crea y ejecuta múltiples hilos utilizando la interfaz Runnable.]
 * FR implementados: [FR1, FR2, FR3]
 */





public class Main {
    public static void main(String[] args) {

        Tarea tarea1 = new Tarea(1, "Tarea 1");
        Tarea tarea2 = new Tarea(2, "Tarea 2");
        Tarea tarea3 = new Tarea(3, "Tarea 3");


        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);

        hilo1.start();
        hilo2.start();
        hilo3.start();
        

      
    }
}

 /*En un comentario al final del código, explica con tus propias palabras:
   Qué diferencia hay entre crear un proceso y crear un hilo:

    - Un proceso es una instancia de un programa en ejecución que tiene su propio espacio de memoria y recursos del sistema, 
    mientras que un hilo es una unidad de ejecución dentro de un proceso que comparte el mismo espacio de memoria y recursos con otros hilos del mismo proceso.*/

 /*Qué ventajas e inconvenientes tiene la programación concurrente.
     
     * Ventajas:
     * 1. Mejora el rendimiento al permitir la ejecución simultánea de múltiples tareas.
     * 2. Mejor utilización de los recursos del sistema.
     * 3. Permite una mejor organización del código al separar tareas independientes.
     * 
     * Inconvenientes:
     * 1. Dificultad en la depuración y el mantenimiento del código debido a la complejidad añadida.
     * 2. Problemas de sincronización y condiciones de carrera si no se manejan adecuadamente los recursos compartidos. (synchronized como vimos en el ejercicio del banco)
     */
