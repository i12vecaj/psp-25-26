/*
 * Nombre: [Alberto Nieto Lozano]
 * Fecha: [29/06/2025]
 * Descripción: [Creación de Tareas con hilos]
 * FR implementados: [FR1, FR2...]
 */

import java.util.Random;

public class simuladortareas {
    public static void main(String[] args) {

        Thread tarea1 = new Thread(new Tarea("Tarea 1"));
        Thread tarea2 = new Thread(new Tarea("Tarea 2"));
        Thread tarea3 = new Thread(new Tarea("Tarea 3"));

        tarea1.start();
        tarea2.start();
        tarea3.start();
    }
}

    class Tarea implements Runnable {
        private String nombre;
        private Random random = new Random();

        public Tarea(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            for (int i = 1; 1 <= 15; i++) { //Le he implementado que corra 15 hilos para ponerle un límite
                System.out.println(nombre + " ejecutandose en el hilo " + i);

                if(i>=15){
                    System.out.println(nombre + " finalizada");
                }

                try {
                    Thread.sleep(500 + random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

// Diferencia entre proceso e hilo:
// Un proceso es un programa en ejecución con su propio espacio de memoria y recursos, siendo la unidad mínima de ejecución.
// Un hilo es un subproceso dentro de un proceso que comparte recursos y memoria con otros hilos del mismo proceso, podiendo realizar multiples tareas.

// Programación concurrente
// La programación concurrente permite que varios procesos e hilos se ejecuten al mismo tiempo aunque no se ejecuten simultaneamente.
