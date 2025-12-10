package app;

import modules.ModuloTrabajo;

public class Main {

    public static void main(String[] args) {
        
        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY); // Prioridad alta
        moduloB.setPriority(Thread.NORM_PRIORITY); // Prioridad media
        moduloC.setPriority(Thread.MIN_PRIORITY); // Prioridad baja

        // Arrancar hilos
        moduloA.start();
        moduloB.start();
        moduloC.start();

        // Comprobación de estado
        System.out.println("¿A está vivo? " + moduloA.isAlive());
        System.out.println("¿B está vivo? " + moduloB.isAlive());
        System.out.println("¿C está vivo? " + moduloC.isAlive());

        // Interrumpir B a los 1.5s
        try {
            Thread.sleep(1500);
            System.out.println("MAIN: Interrumpiendo módulo B...");
            moduloB.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Esperar a que terminen
        try {
            moduloA.join();
            System.out.println("Módulo A finalizado. Estado vivo: " + moduloA.isAlive());

            moduloB.join();
            System.out.println("Módulo B finalizado. Estado vivo: " + moduloB.isAlive());

            moduloC.join();
            System.out.println("Módulo C finalizado. Estado vivo: " + moduloC.isAlive());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar información al finalizar todo el proceso
        System.out.println("\n Información final de módulos:");
        System.out.println(moduloA);
        System.out.println(moduloB);
        System.out.println(moduloC);

        /*
         * Observaciones:
         * La prioridad de los hilos influye en la planificación, pero no determina su orden real de ejecución y es el sistema operativo quien decide finalmente qué hilo se ejecuta en cada momento.
         *
         * El método yield() únicamente indica al planificador que el hilo actual está dispuesto a ceder la CPU, pero esta cesión no está garantizada. Su efecto depende completamente de la política que tenga el sistema operativo.
         *
         * La interrupción solo provoca la detención anticipada del hilo si este se encuentra en un estado bloqueado, como durante una llamada a sleep(). En ese caso se lanza InterruptedException y el hilo puede finalizar de forma controlada.
         *
         * La planificación y el orden de ejecución de los hilos son totalmente dependientes del sistema operativo. Por ello, dos ejecuciones consecutivas del mismo programa pueden producir secuencias distintas.
         */
    }
}
