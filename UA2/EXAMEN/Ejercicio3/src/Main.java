import java.util.Random;

public class Main {
    public static void main(String[] args) {

        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY);
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread.MIN_PRIORITY);

        moduloA.start();
        moduloB.start();
        moduloC.start();

        System.out.println("Módulo A vivo: " + moduloA.isAlive());
        System.out.println("Módulo B vivo: " + moduloB.isAlive());
        System.out.println("Módulo C vivo: " + moduloC.isAlive());

        try {
            Thread.sleep(1500);
            moduloB.interrupt();

            moduloA.join();
            System.out.println("Módulo A ha finalizado. Estado vivo: " + moduloA.isAlive());
            moduloB.join();
            System.out.println("Módulo B ha finalizado. Estado vivo: " + moduloB.isAlive());
            moduloC.join();
            System.out.println("Módulo C ha finalizado. Estado vivo: " + moduloC.isAlive());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(moduloA);
        System.out.println(moduloB);
        System.out.println(moduloC);
    }
}

class ModuloTrabajo extends Thread {
    private String nombre;
    private static final Random random = new Random();

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado. ID: " + getId());
        for (int i = 0; i <= 5; i++) {
            try {
                System.out.println("Módulo " + nombre + " iteracción " + i);
                if (i == 3) {
                    Thread.yield();
                }
                Thread.sleep(300 + random.nextInt(500));
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
            }
        }
    }

    @Override
    public String toString() {
        return "MóduloTrabajo{nombre='" + nombre + "', id=" + getId() + ", prioridad=" + getPriority() + "}";
    }
}

