public class Main {
    public static void main(String[] args) throws InterruptedException {

        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY);
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread.MIN_PRIORITY);

        moduloA.start();
        moduloB.start();
        moduloC.start();

        System.out.println("Comprobacion");
        System.out.println("Módulo A: " + moduloA.isAlive());
        System.out.println("Módulo B: " + moduloB.isAlive());
        System.out.println("Módulo C: " + moduloC.isAlive());

        Thread.sleep(1500);
        moduloB.interrupt();
        System.out.println("Hilo principal ha interrumpio al B ");

        try {
            moduloA.join();
            System.out.println("Módulo A finalizado " + moduloA.isAlive());

            moduloB.join();
            System.out.println("Módulo B finalizado " + moduloB.isAlive());

            moduloC.join();
            System.out.println("Módulo C finalizado " + moduloC.isAlive());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Info final");
        System.out.println(moduloA.toString());
        System.out.println(moduloB.toString());
        System.out.println(moduloC.toString());
    }
}