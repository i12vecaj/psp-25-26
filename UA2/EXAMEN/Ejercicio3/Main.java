
/*-----------------------------------------------------------
Ejercicio de Examen: Programación Multihilo en Java
-----------------------------------------------------------
La empresa TechFactory quiere simular el comportamiento de varios módulos de producción que trabajan
simultáneamente. Cada módulo es un hilo que realiza tareas simples, pero deben coordinarse y mostrar información relevante de su ejecución.


*/


import java.util.Random;

public class Main {
    public static void main(String[] args) {

/*
        -----------------------------------------------------------
                FR2 (2 puntos) – Prioridades y arranque de hilos
                -----------------------------------------------------------
                En el main:
        Crea tres objetos de tipo ModuloTrabajo.
        Asigna prioridades distintas con setPriority():
        Módulo A → prioridad alta
        Módulo B → prioridad media
        Módulo C → prioridad baja
        Arranca los hilos usando start().
                */



        ModuloTrabajo moduloA=new ModuloTrabajo("Módulo A");
        moduloA.setPriority(10);

        ModuloTrabajo moduloB=new ModuloTrabajo("Módulo B");
        moduloB.setPriority(10);

        ModuloTrabajo moduloC=new ModuloTrabajo("Módulo C");
        moduloC.setPriority(10);

        moduloA.start();
        moduloB.start();
        moduloC.start();

/*
        -----------------------------------------------------------
                FR3 (2 puntos) – Comprobación del estado de los hilos
        -----------------------------------------------------------
                Tras iniciar los hilos, en main:
        Muestra por pantalla si cada hilo está vivo con isAlive().
                Espera a que todos terminen con join().
                Cuando cada hilo finalice, muestra:
        "Módulo <nombre> finalizado. Estado vivo: <isAlive()>".

 */

        //No recuerdo como funcionaba join
        System.out.println("True=vivo False=muerto");
        System.out.println("Módulo 1 está "+moduloA.isAlive());
        System.out.println("Módulo 2 está "+moduloB.isAlive());
        System.out.println("Módulo 3 está "+moduloC.isAlive());



/*
        -----------------------------------------------------------
                FR4 (2 puntos) – Interrupción controlada
        -----------------------------------------------------------
                Modifica el main para:
        Interrumpir el Módulo B después de 1.5 segundos.
                En el run(), controla la interrupción:
        Si el hilo es interrumpido durante el sleep(), captura InterruptedException y muestra:
        "Módulo <nombre> interrumpido durante la ejecución"
        Finaliza el hilo inmediatamente tras la interrupción.

 */
        moduloB.interrupt();

        //No recuerdo aparte de stop que mas paraba por completo el hilo
        //modulo2.stop();


        moduloA.toString();
        moduloB.toString();
        moduloC.toString();


    }
}



/*
-----------------------------------------------------------
FR1 (2 puntos) – Crear la clase MóduloTrabajo
-----------------------------------------------------------
Crea una clase llamada ModuloTrabajo que extienda de Thread.
Debe incluir:
Un nombre del módulo (recibido por el constructor).
Un método run() que:
Muestre el mensaje:
        "Módulo <nombre> iniciado. ID: <id del hilo>"
Realice un bucle de 5 iteraciones donde:
Imprima "Módulo <nombre> – iteración X"
Haga un sleep() entre 300 y 800 ms (aleatorio).
En la tercera iteración llame a yield().

 */

class ModuloTrabajo extends Thread {

    String nombreDelModulo;
    Random random = new Random();

    ModuloTrabajo(String nombreDelModulo) {
        this.nombreDelModulo = nombreDelModulo;
    }



    @Override
    public void run() {



            try {
                System.out.println(nombreDelModulo + " iniciado con: ID: " + Thread.currentThread().threadId());


                for (int i = 0; i < 5; i++) {
                    System.out.println("Módulo" + nombreDelModulo + " iniciado se encuentra en la iteración " + i);

                    Thread.sleep(1000 + random.nextInt(1500));


                    if (i == 3) {
                        Thread.yield();
                    }

                }
            } catch (InterruptedException e) {
                System.out.println("Hilo con ID "+Thread.currentThread().threadId() +" interrumpido, mensaje de error: "+ e.getMessage());

            }


        System.out.println(nombreDelModulo+" finalizado con. Estado vivo: "+Thread.currentThread().isAlive());


    }//Fin del run



    /*
    -----------------------------------------------------------
    FR5 (2 puntos) – Información final y método toString()
-----------------------------------------------------------
    Implementa en ModuloTrabajo el método toString() para que devuelva:
            "MóduloTrabajo{nombre='X', id=ID_DEL_HILO, prioridad=PRIORIDAD}"
    En el main, al finalizar todo el proceso:
    Muestra toString() de los tres módulos.
    Explica brevemente (4–5 líneas, dentro del código como comentario) qué has observado respecto a:
    priorización de hilos,
    yield(),
    interrupción,
    planificación del sistema operativo.

     */

    @Override
    public String toString() {
        return "ModuloTrabajo{ nombre="+nombreDelModulo+" id= "+Thread.currentThread().threadId()+" prioridad= "+Thread.currentThread().getPriority()+" }";
    }
}