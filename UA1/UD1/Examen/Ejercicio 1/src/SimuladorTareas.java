/*
*Nombre: Juan José Rojano Doncel
*Fecha: 29/10/2025
*Descripción:Implementar runnable y run de manera que, tengamos 3 tareas, dormirlas en un intervalo de segundos e imprimir nombre de hilo
*FR implementados:
* */


import java.util.Random;

public class SimuladorTareas {
    public static void main(String[] args) {

        /*d) Desde el método main, crea y lanza tres hilos que ejecuten las tres tareas anteriores.
        (2 puntos)*/

        Tareas hilo1 = new Tareas("Tarea 1",1);
        Tareas hilo2 = new Tareas("Tarea 2",2);
        Tareas hilo3 = new Tareas("Tarea 3",3);

        Thread t1 = new Thread(hilo1);
        Thread t2 = new Thread(hilo2);
        Thread t3 = new Thread(hilo3);

        t1.start();
        t2.start();
        t3.start();
    }



}

/*a) Crea una clase interna llamada Tarea que implemente la interfaz Runnable.
Cada tarea debe tener un nombre (por ejemplo, "Tarea 1", "Tarea 2", "Tarea 3") que se reciba por parámetro en el constructor.
        (1 punto)*/

class Tareas implements Runnable {

    private String numeroTarea;
    private int hilo;
    private Random random = new Random();


    public Tareas(String numeroTarea, int hilo) {
        this.numeroTarea = numeroTarea;
        this.hilo = hilo;

    }

    /*b) En el método run() de cada tarea, muestra por pantalla un mensaje indicando que la tarea ha comenzado, el identificador del hilo actual y el nombre de la tarea.
    Por ejemplo:[Tarea 1] ejecutándose en el hilo 13
            (1,5 puntos)*/
    public void run(){

        int hiloCont=0;

        System.out.println("["+numeroTarea+"]"+" ejecutándose en el hilo "+hilo);


        /*c) Haz que cada tarea simule un trabajo real durmiendo entre 500 y 1500 milisegundos (usa Thread.sleep()) antes de finalizar.
                Al terminar, debe mostrar: [Tarea 1] finalizada.
        (1,5 puntos)*/
        for (int i = 1; i <= 6; i++) {

            hiloCont++;

            System.out.println("La "+numeroTarea+" tiene de valor "+hiloCont);
            try {
                Thread.sleep(1000 + random.nextInt(1500));
            }catch(Exception e){
                System.out.println("Error en el sleep "+e.getMessage());
            }

        }
        /*e) Asegúrate de que el hilo principal muestre su propio identificador antes y después de la ejecución de las tareas, y que espere a que todas terminen antes de finalizar.
        (2 puntos)*/
        System.out.println("["+numeroTarea+"] con hilo "+hilo+" finalizado");


    }


}

/*
* f) En un comentario al final del código, explica con tus propias palabras:
Qué diferencia hay entre crear un proceso y crear un hilo.
*
*El proceso pasará a cola detrás de otros procesos los cuales, compiten por los recursos
* y se comunican entre ellos para evitar ambiguedades (se le llama concurrencia) mientras queç
* los hilos aunque tienen cierta concurrencia, al crearlos estos trabajan en paralelo
*
Qué ventajas e inconvenientes tiene la programación concurrente.
*
* Desventajas:
*
* Los procesos compiten entre si para poder acceder a los recursos del sistema
* Los procesos se ejecutan uno detrás de otro siendo un proceso en ocasiones lentos
* Es menos escalable a diferencia de la programación distribuida
*
* Ventajas:
*
* Es más fácil sincronizar los procesos
* Es más facil comunicar los procesos unos con otros
*
*
*
*(2 puntos)

* */