/*
*
    FR1: Crea un programa que reciba a través de sus argumentos una lista de ficheros de texto (procura que sean de un cierto tamaño,
    por ejemplo El Quijote.txt) y cuente el número de caracteres que hay en cada fichero (ejecución secuencial).
    *  Ayuda en este enlace - 2 puntos.
    FR2: Modifica el programa para que cree un hilo para cada fichero (ejecución concurrente) - 2 puntos.
    FR3: Compara el tiempo de ejecución entre los dos apartados anteriores - 2 puntos.
    Implementa el control de errores básico - 2 puntos
    Documenta el código indicando el funcionamiento del programa y las diferencias que has observado entre el primer y el segundo apartado - 2 puntos
    Entregables:
        tareas\dev_X\tarea_3\*
 */

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        //Crear los Hilos Secuencial

        Hilo1 hilo1 = new Hilo1("Quijote.txt");
        Hilo1 hilo2 = new Hilo1("doc1.txt");
        Hilo1 hilo3 = new Hilo1("doc2.txt");

        System.out.println("=================");
        System.out.println("Hilos Secuencial");
        System.out.println("=================");
        //Hilos en ejecucion
        hilo1.run();
        hilo2.run();
        hilo3.run();
        System.out.println("=================");
        // Crear Hilo Concurrente
        System.out.println("Hilos Concurrente");
        System.out.println("==================");

        Hilo1C tarea1 = new Hilo1C("Quijote.txt");
        Hilo1C tarea2 = new Hilo1C("doc1.txt");
        Hilo1C tarea3 = new Hilo1C("doc2.txt");

        Thread h1c = new Thread(tarea1);
        Thread h1d = new Thread(tarea2);
        Thread h3c = new Thread(tarea3);
        //Ejecucion de hilo
        h1c.start();
        h1d.start();
        h3c.start();


        try {
            h1c.join();
            h1d.join();
            h3c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
