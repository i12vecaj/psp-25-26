public class Main {

    public static void main(String[] args) {

        ModuloTrabajo modulo1 = new ModuloTrabajo("Modulo A", Thread.NORM_PRIORITY);
        ModuloTrabajo modulo2 = new ModuloTrabajo("Modulo B", Thread.NORM_PRIORITY + 2);
        ModuloTrabajo modulo3 = new ModuloTrabajo("Modulo C", Thread.NORM_PRIORITY - 2);

        Thread hilo1 = new Thread(modulo1);
        Thread hilo2 = new Thread(modulo2);
        Thread hilo3 = new Thread(modulo3);

        hilo1.setPriority(modulo1.prioridad);

        hilo2.setPriority(modulo2.prioridad);
        hilo3.setPriority(modulo3.prioridad);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            System.out.println("Hilo 1 vivo: " + hilo1.isAlive());
            System.out.println("Hilo 2 vivo: " + hilo2.isAlive());
            System.out.println("Hilo 3 vivo: " + hilo3.isAlive());

            hilo1.join();
            System.out.println("Módulo " + modulo1.nombre + " finalizado. Estado vivo: " + hilo1.isAlive());

            hilo2.join();
            System.out.println("Módulo " + modulo2.nombre + " finalizado. Estado vivo: " + hilo2.isAlive());

            hilo3.join();
            System.out.println("Módulo " + modulo3.nombre + " finalizado. Estado vivo: " + hilo3.isAlive());

        } catch (InterruptedException e) {
            System.out.println("Main interrumpido");
        }
        try {
            Thread.sleep(1500);
            hilo2.interrupt();
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido durante el sleep");
        }
        System.out.println(modulo1.toString());
        System.out.println(modulo2.toString());
        System.out.println(modulo3.toString());
    }
}
