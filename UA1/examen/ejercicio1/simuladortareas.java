 /*
 * Nombre: [Alejandro Prieto Mellado]
 * Fecha: [29/10/2025]
 * Descripción: [Simulador de tareas
 * FR implementados: [FR1, FR2...]
 */

 /*
 a) Crea una clase interna llamada Tarea que implemente la interfaz Runnable.
Cada tarea debe tener un nombre (por ejemplo, "Tarea 1", "Tarea 2", "Tarea 3") que se reciba por parámetro en el constructor.
(1 punto)

b) En el método run() de cada tarea, muestra por pantalla un mensaje indicando que la tarea ha comenzado, el identificador del hilo actual y el nombre de la tarea.
Por ejemplo:[Tarea 1] ejecutándose en el hilo 13
(1,5 puntos)

c) Haz que cada tarea simule un trabajo real durmiendo entre 500 y 1500 milisegundos (usa Thread.sleep()) antes de finalizar.
Al terminar, debe mostrar: [Tarea 1] finalizada.
(1,5 puntos)

d) Desde el método main, crea y lanza tres hilos que ejecuten las tres tareas anteriores.
(2 puntos)

e) Asegúrate de que el hilo principal muestre su propio identificador antes y después de la ejecución de las tareas, y que espere a que todas terminen antes de finalizar.
(2 puntos)

f) En un comentario al final del código, explica con tus propias palabras:
Qué diferencia hay entre crear un proceso y crear un hilo.
Qué ventajas e inconvenientes tiene la programación concurrente.
(2 puntos) 
*/

package ejercicio1;

import java.util.Random;

public class simuladortareas {
    public static void main(String[] args) {
        Thread t1 = new Thread(new tarea("Tarea1"));
        Thread t2 = new Thread(new tarea("Tarea2"));
        Thread t3 = new Thread(new tarea("Tarea3"));

        t1.start();
        t2.start();
        t3.start();

    }
}

class tarea implements Runnable{
    private String nombre;
    private Random random;


    public tarea(String nombre) {
        this.nombre = nombre;
        this.random = new Random();
    }


    @Override
    public void run() {
        for(int i = 1; i <= 20;i++){
            System.out.println("["+nombre +"] "+ "ejecutandose en hilo: " + i + "");

            if(i == 20){
                System.out.println("["+nombre +"] " + "Finalizada");
            }

            try{
                Thread.sleep(500 + random.nextInt(1000));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        } 
    }
}

/*
 * f) En un comentario al final del código, explica con tus propias palabras:
 * Qué diferencia hay entre crear un proceso y crear un hilo.
 * Qué ventajas e inconvenientes tiene la programación concurrente.
 * (2 puntos) 
 * 
 * 
 * que crear un proceso es un programa en ejecucion que ocupa su propio espacio en recursos y en memoria
 * mientras que los hilos son subprocesos que pueden trabajar de manera simultanea en varias tareas compartiendo recursos y memoria 
 * 
 * 
 * la programacion concurrente permite que varios procesos he hilos se ejecuten simultaneamente, osea que se puede alternar en el tiempo
 * 
 * y la desventaja es que no peden trabajar en distintas tareas como si pasa en la programacion paralela
 */


