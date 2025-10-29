
/*
 * Nombre: Bernardo Cubero Martínez

 * Fecha: 29/10/2025

 * Descripción: Simulador de Tareas que va mostrando en consola

 * FR implementados:
 *  FR1 implementado
 *  FR2 implentando
 *  FR3 implementado
 *  FR4 implementado
 *  FR5 implementado
 *  FR6 implementado
 */
public class Main {
    public static void main(String[] args) {

        //Creación del objeto
        Tarea1 t1 = new Tarea1(3);
        Tarea2 t2 = new Tarea2(2);
        Tarea3 t3 = new Tarea3(5);

        //Creacion del hilo
        Thread hilo1 = new Thread(t1);
        Thread hilo2 = new Thread(t2);
        Thread hilo3 = new Thread(t3);

        //Se lanzan los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();

        }
    }
/*
En un comentario al final del código, explica con tus propias palabras:
Qué diferencia hay entre crear un proceso y crear un hilo.
    El proceso es un programa que se ejecuta con unos argumentos para que se procese
    El hilo es cuando dos procesos se intercalan y además colaboran para optimizar los recursos y compiten por ellos
    Las ventajas de la Programación Concurrente:
    Optimiza los recursos con una buena comunicación entre los procesos o hilos
    para no saturar el sistema.
    Inconveniente:
    Si no existe una buena comunicación el sistema se puede bloquear y pierde efectividad.
    También si no optimizamos podemos peder tiempo de CPU entre que un hilo termina y otro que ya esta esperando
    Existe la Exclusion mutua y eso de debe a la posiblidad de que dos procesos intenten acceder a la misma variable


Qué ventajas e inconvenientes tiene la programación concurrente
 */