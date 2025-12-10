import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //Creacion de Hilos
        ModuloTrabajo tareaA = new ModuloTrabajo(" A");
        ModuloTrabajo tareaB = new ModuloTrabajo(" B");
        ModuloTrabajo tareaC = new ModuloTrabajo(" C");

        //Selecinamos Prioriadaes para cada Hiloa
        tareaA.setPriority(Thread.MAX_PRIORITY);
        tareaB.setPriority(Thread.NORM_PRIORITY);
        tareaC.setPriority(Thread.MIN_PRIORITY);

        //Arrancamos los Hilos
        tareaA.start();
        tareaB.start();
        tareaC.start();

        //Mostramos los hilos como estan
        System.out.println("Estado inicial de los hilos ");
        System.out.println("A está vivo: " + tareaA.isAlive());
        System.out.println("B está vivo: " + tareaB.isAlive());
        System.out.println("C está vivo: " + tareaC.isAlive());


        try {
            Thread.sleep(1500);
            System.out.println("Interrumpiendo Tarea B");
            tareaB.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            tareaA.join();
            System.out.println("Tarea a finalizado. Estado vivo: " + tareaA.isAlive());

            tareaB.join();
            System.out.println("Tarea b finalizado. Estado vivo: " + tareaB.isAlive());

            tareaC.join();
            System.out.println("Tarea c finalizado. Estado vivo: " + tareaC.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Información Final de los Tareas");
        System.out.println(tareaA.toString());
        System.out.println(tareaB.toString());
        System.out.println(tareaC.toString());

    }
}


class ModuloTrabajo extends Thread {
    private String nombre;
    private Random aleatorio;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
        this.aleatorio = new Random();
    }

    @Override
    public void run() {


        try {
            for (int i = 1; i <= 5; i++) {

                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Tarea " + nombre );
                    return;
                }

                System.out.println("Tarea " + nombre + " iteración " + i);


                if (i == 3) {
                    System.out.println("Tarea " + nombre );
                    Thread.yield();
                }


                int sleepTime;
                sleepTime = 300 + aleatorio.nextInt(500);
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {

            System.out.println("Tarea " + nombre);
            return;
        }

        System.out.println("Tarea " + nombre + " termino");
    }


    @Override
    public String toString() {
        return "Nombre: " + nombre + ", id: " + this.getId() +
                ", prioridad: " + this.getPriority();
    }
}
/*
Como ya dijimos en la parte de  fundamentos,
yield() actua como recomendador del sistema operativo
asi que el sistema operativo es el que al final hace las prioridades.
 */