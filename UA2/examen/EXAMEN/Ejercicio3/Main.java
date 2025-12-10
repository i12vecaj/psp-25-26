/*
Nombre: Jose Antonio Roda Donoso
Fecha: 10/12/2025
Curso: 2 DAM
Crea tres módulos de trabajo, asigna prioridades distintas, y muestra informacion
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Crear los módulos y asignar prioridades
        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY);
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread.MIN_PRIORITY);

        // Arrancar los hilos
        moduloA.start();
        moduloB.start();
        moduloC.start();

        // Mostrar si están vivos
        System.out.println("Estado inicial hilos:");
        System.out.println("Módulo A vivo? " + moduloA.isAlive());
        System.out.println("Módulo B vivo? " + moduloB.isAlive());
        System.out.println("Módulo C vivo? " + moduloC.isAlive());

        // Interrumpir Módulo B después de 1.5 s
        Thread.sleep(1500);
        System.out.println("Interrumpiendo Módulo B");
        moduloB.interrupt();

        // Esperar que todos terminen
        moduloA.join();
        System.out.println("Módulo A finalizado. Estado vivo: " + moduloA.isAlive());

        moduloB.join();
        System.out.println("Módulo B finalizado. Estado vivo: " + moduloB.isAlive());

        moduloC.join();
        System.out.println("Módulo C finalizado. Estado vivo: " + moduloC.isAlive());

        System.out.println("Información final de los módulos:");
        System.out.println(moduloA);
        System.out.println(moduloB);
        System.out.println(moduloC);

        /*
         - Que un hilo tenga más prioridad no significa que siempre se ejecute antes que los demás
         - yield(): hace que el hilo deje paso a otros
         - Interrumpir un hilo hace que pare de forma controlada si está dormido o trabajando
         - El sistema operativo decide en qué orden corren los hilos
        */
    }
}