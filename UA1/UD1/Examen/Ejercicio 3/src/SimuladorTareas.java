/*
*Nombre: Juan José Rojano Doncel
*Fecha: 29/10/2025
*Descripción: Simular el trabajo en casa, crear varias clases que implementen runable,
* y llamarlas desde main escribiendo por consola información
*
*FR implementados:
* */


/*OBSERVACIÓN DEL ALUMNO*/

/*Quise intentar crear crear una clase llamada (trabajosHogar) la cual crease los hilos de Cocinar, Lavar
* Limpiar...pero leí de nuevo el enunciado y pensé (no estoy seguro de que quiera eso)
*
* Así que, dejo este mensaje para preguntarte, ¿habría hecho bien?*/

import java.util.Random;

public class SimuladorTareas {
    public static void main(String[] args) {

/*FR4 (2 puntos):
Desde el método main, crear y arrancar los tres hilos de forma concurrente, mostrando también un mensaje al inicio y al final del programa.*/


        LavarRopa hilo1 = new LavarRopa("Lavando ropa del padre");
        Cocinar hilo2 = new Cocinar("Cocinando para los invitados");
        Limpiar hilo3 = new Limpiar("Limpiando casa");

        Thread t1 = new Thread(hilo1);
        Thread t2 = new Thread(hilo2);
        Thread t3 = new Thread(hilo3);

        t1.start();
        t2.start();
        t3.start();
    }



}

/*
FR1 (2 puntos):
Crear tres clases que implementen Runnable, una para cada tarea: LavarRopa, Cocinar y Limpiar.
 */
class LavarRopa implements Runnable {

    private String numeroTarea;
    private Random random = new Random();


    public LavarRopa(String numeroTarea) {
        this.numeroTarea = numeroTarea;

    }
/*
    FR2 (2 puntos):
    En el método run() de cada clase, mostrar un mensaje al iniciar y otro al finalizar la tarea.
    Por ejemplo:
            [Lavar ropa] Comenzando tarea...
            [Lavar ropa] Tarea finalizada.
 */
    public void run(){

        int hiloCont=0;

        System.out.println("["+numeroTarea+"]"+" iniciada");



        for (int i = 1; i <= 6; i++) {

            hiloCont++;

            System.out.println("La tarea"+numeroTarea+" está siendo realizada, lavando ropa "+hiloCont);
            try {
                /*FR3 (2 puntos):
Simular que cada tarea tarda un tiempo distinto en completarse usando Thread.sleep() (por ejemplo, 1, 2 y 3 segundos).*/
                Thread.sleep(1000 + random.nextInt(3000));
            }catch(Exception e){
                System.out.println("Error en el sleep "+e.getMessage());
            }

        }

        System.out.println("["+numeroTarea+"] finalizada");
    }
}











class Cocinar implements Runnable {

    private String numeroTarea;
    private Random random = new Random();


    public Cocinar(String numeroTarea) {
        this.numeroTarea = numeroTarea;

    }

    /*
        FR2 (2 puntos):
        En el método run() de cada clase, mostrar un mensaje al iniciar y otro al finalizar la tarea.
        Por ejemplo:
                [Lavar ropa] Comenzando tarea...
                [Lavar ropa] Tarea finalizada.
     */
    public void run(){

        int hiloCont=0;

        System.out.println("["+numeroTarea+"]"+" iniciada");



        for (int i = 1; i <= 6; i++) {

            hiloCont++;

            System.out.println("La tarea"+numeroTarea+" está siendo realizada, cocinando el plato "+hiloCont);
            try {
                /*FR3 (2 puntos):
Simular que cada tarea tarda un tiempo distinto en completarse usando Thread.sleep() (por ejemplo, 1, 2 y 3 segundos).*/

                Thread.sleep(1000 + random.nextInt(3000));
            }catch(Exception e){
                System.out.println("Error en el sleep "+e.getMessage());
            }

        }

        System.out.println("["+numeroTarea+"] finalizada");
    }
}




class Limpiar implements Runnable {

    private String numeroTarea;
    private Random random = new Random();


    public Limpiar(String numeroTarea) {
        this.numeroTarea = numeroTarea;

    }

    /*
        FR2 (2 puntos):
        En el método run() de cada clase, mostrar un mensaje al iniciar y otro al finalizar la tarea.
        Por ejemplo:
                [Lavar ropa] Comenzando tarea...
                [Lavar ropa] Tarea finalizada.
     */
    public void run(){

        int hiloCont=0;

        System.out.println("["+numeroTarea+"]"+" iniciada");



        for (int i = 1; i <= 6; i++) {

            hiloCont++;

            System.out.println("La tarea"+numeroTarea+" está siendo realizada, limpiando la habitación "+hiloCont);
            try {
                /*FR3 (2 puntos):
Simular que cada tarea tarda un tiempo distinto en completarse usando Thread.sleep() (por ejemplo, 1, 2 y 3 segundos).*/

                Thread.sleep(1000 + random.nextInt(3000));
            }catch(Exception e){
                System.out.println("Error en el sleep "+e.getMessage());
            }

        }

        System.out.println("["+numeroTarea+"] finalizada");
    }
}