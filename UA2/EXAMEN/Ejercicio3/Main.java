
public class Main {
    public static void main(String[] args) {

        // Crear instancias de los módulos de trabajo

        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        // Asignar prioridades diferentes a los hilos
        moduloA.setPriority(Thread.MAX_PRIORITY);
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread.MIN_PRIORITY);

        // Iniciar los hilos
        moduloA.start();
        moduloB.start();
        moduloC.start();

        // Mostrar estado inicial de los hilos
        System.out.println("\nHilos iniciados:");
        System.out.println("Módulo A vivo: " + moduloA.isAlive());
        System.out.println("Módulo B vivo: " + moduloB.isAlive());
        System.out.println("Módulo C vivo: " + moduloC.isAlive());

        // Interrumpir el hilo del módulo B después de 1.5 segundos
        try {
            Thread.sleep(1500);
            moduloB.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Esperar a que los hilos terminen
        try {
            moduloA.join();
            moduloB.join();
            moduloC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar estado final de los hilos
        System.out.println("\nHilos finalizados:");
        System.out.println("Módulo A finalizado. Estado final: " + moduloA.isAlive());
        System.out.println("Módulo B finalizado. Estado final: " + moduloB.isAlive());
        System.out.println("Módulo C finalizado. Estado final: " + moduloC.isAlive());

        // Mostrar información de los módulos
        System.out.println("\nInformación de módulos:");
        System.out.println(moduloA.toString());
        System.out.println(moduloB.toString());
        System.out.println(moduloC.toString());

        System.out.println("\nPrograma finalizado.");
    }
}

class ModuloTrabajo extends Thread {
    private String nombre;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado. ID: " + this.threadId());
        for (int i = 1; i <= 5; i++) {
            System.out.println("Módulo " + nombre + " iteración " + i);
            try {
                if (i == 3) {
                    Thread.yield();
                }
                Thread.sleep(600);
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "MóduloTrabajo{nombre='" + nombre + "', id=" + this.threadId() + ", prioridad=" + this.getPriority()
                + "}";
    }
}
/*
 * Aun dando distintas prioridades a los hilos se ve que sigue bastante
 * aleatorio su orden de ejecucion
 */
