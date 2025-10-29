/*
 * Nombre: Eduardo Ruz López
 * Fecha: 29/10/2025
 * Descripción: Simulamos las tareas de una casa y las ejecutamos mediante hilos
 * FR implementados: FR1,FR2,FR3,FR4,FR5
 */

public class Main {
    public static void main(String[] args){

        System.out.println("Inicio del programa");

        LavarRopa lavar = new LavarRopa();
        Cocinar cocinar = new Cocinar();
        Limpiar limpiar = new Limpiar();

        Thread t1 = new Thread(lavar);
        Thread t2 = new Thread(cocinar);
        Thread t3 = new Thread(limpiar);

        t1.start();
        t2.start();
        t3.start();

    }
}
