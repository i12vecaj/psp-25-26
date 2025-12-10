/* -----------------------------------------------------------
Ejercicio de Examen: Programación Multihilo en Java
-----------------------------------------------------------
La empresa TechFactory quiere simular el comportamiento de varios módulos de producción que trabajan 
simultáneamente. Cada módulo es un hilo que realiza tareas simples, pero deben coordinarse y mostrar información 
relevante de su ejecución.
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
-----------------------------------------------------------
FR3 (2 puntos) – Comprobación del estado de los hilos
-----------------------------------------------------------
Tras iniciar los hilos, en main:
Muestra por pantalla si cada hilo está vivo con isAlive().
Espera a que todos terminen con join().
Cuando cada hilo finalice, muestra:
"Módulo <nombre> finalizado. Estado vivo: <isAlive()>".
-----------------------------------------------------------
FR4 (2 puntos) – Interrupción controlada
-----------------------------------------------------------
Modifica el main para:
Interrumpir el Módulo B después de 1.5 segundos.
En el run(), controla la interrupción:
Si el hilo es interrumpido durante el sleep(), captura InterruptedException y muestra:
"Módulo <nombre> interrumpido durante la ejecución"
Finaliza el hilo inmediatamente tras la interrupción.
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
planificación del sistema operativo. */

package Ejercicio3;

public class Main {
    public static void main(String[] args) {
        ModuloTrabajo moduloA = new ModuloTrabajo("A"); //crear los modulos
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY); //asignar prioridades
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread.MIN_PRIORITY);

        moduloA.start(); //arrancar los hilos
        moduloB.start();
        moduloC.start();

        System.out.println("Módulo A está vivo: " + moduloA.isAlive()); //comprobar si los hilos estan vivos
        System.out.println("Módulo B está vivo: " + moduloB.isAlive());
        System.out.println("Módulo C está vivo: " + moduloC.isAlive());

        try {
            Thread.sleep(1500); //esperar 1.5 segundos antes de interrumpir el B
            moduloB.interrupt();// interrumpirlo
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        try {
            moduloA.join();
            System.out.println("Módulo A finalizado. Estado vivo: " + moduloA.isAlive()); //esperar a que terminen los hilos y ver
            moduloB.join();                                                               //su estado                                                         
            System.out.println("Módulo B finalizado. Estado vivo: " + moduloB.isAlive());
            moduloC.join();
            System.out.println("Módulo C finalizado. Estado vivo: " + moduloC.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.out.println(moduloA.toString()); //mostrar la informacion final de los modulos
        System.out.println(moduloB.toString());
        System.out.println(moduloC.toString());
    }
}
