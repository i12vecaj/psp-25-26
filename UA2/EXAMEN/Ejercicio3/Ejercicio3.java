/*
 * Nombre: Pablo Herrador Castillo
 * Fecha: 10/12/2025
 * Descripción: Programación Multihilo en Java
 * FR implementados: FR1, FR2 , FR3, FR4 , FR5
 */


import java.util.Random;


// FR1: Crear clase ModuloTrabajo

class ModuloTrabajo extends Thread {
    private final String nombre;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado. ID: " + getId());
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            try {
                System.out.println("Módulo " + nombre + " – iteración " + i);
                if (i == 3) {
                    System.out.println("Módulo " + nombre + " llama a yield()");
                    Thread.yield();
                }
                Thread.sleep(300 + random.nextInt(500)); // Sleep entre 300 y 800 ms
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
                return; // Finaliza el hilo inmediatamente
            }
        }
    }

    @Override
    public String toString() {
        return "ModuloTrabajo{nombre='" + nombre + "', id=" + getId() + ", prioridad=" + getPriority() + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        // FR2: Crear los módulos y asignar prioridades
        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY); // Prioridad alta
        moduloB.setPriority(Thread.NORM_PRIORITY); // Prioridad media
        moduloC.setPriority(Thread.MIN_PRIORITY); // Prioridad baja

        // Iniciar los hilos
        moduloA.start();
        moduloB.start();
        moduloC.start();

        // FR3: Comprobación del estado de los hilos
        System.out.println("Módulo A está vivo: " + moduloA.isAlive());
        System.out.println("Módulo B está vivo: " + moduloB.isAlive());
        System.out.println("Módulo C está vivo: " + moduloC.isAlive());

        // FR4: Interrumpir el módulo B después de 1.5 segundos
        try {
            Thread.sleep(1500);
            moduloB.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Esperar a que todos los hilos terminen
        try {
            moduloA.join();
            moduloB.join();
            moduloC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar el estado final de los hilos
        System.out.println("Módulo A finalizado. Estado vivo: " + moduloA.isAlive());
        System.out.println("Módulo B finalizado. Estado vivo: " + moduloB.isAlive());
        System.out.println("Módulo C finalizado. Estado vivo: " + moduloC.isAlive());

        // FR5: Mostrar información final
        System.out.println(moduloA);
        System.out.println(moduloB);
        System.out.println(moduloC);

        
    }
}

// Observaciones
        /*
         * Observaciones:
         * 1. Prioridad: Los hilos con mayor prioridad tienden a ejecutarse más frecuentemente, pero no siempre debido a la planificación del sistema operativo.
         * 2. Yield: Permite ceder el control a otros hilos, pero su efecto depende del sistema operativo.
         * 3. Interrupción: El hilo B se interrumpe correctamente durante el sleep, mostrando el mensaje y finalizando su ejecución.
         * 4. Planificación: La planificación de hilos depende del sistema operativo, por lo que el orden de ejecución puede variar.
         */
