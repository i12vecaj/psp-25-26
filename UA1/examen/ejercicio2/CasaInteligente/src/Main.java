 /*

  * Nombre: Bernardo Cubero

  * Fecha: 29/10/2025

  * Descripción: Tareas de una casa inteligente

  * FR implementados:
   FR1: implementado
   FR2: implementaod
   FR3: implementado
   FR4: implementado
   FR5: implemantado

  */


public class Main {
    public static void main(String[] args) {

        LavarRopa lv1 = new LavarRopa(5);
        Cocinar c1 = new Cocinar(6);
        Limpiar l1 = new Limpiar(7);


        Thread t1 = new Thread(lv1);
        Thread t2 = new Thread(c1);
        Thread t3 = new Thread(l1);


        t1.start();
        t2.start();
        t3.start();


    }
}