public class Main {
    public static void main(String[] args) {
        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY);
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread. MIN_PRIORITY);

        moduloA.start();
        moduloB.start();
        moduloC.start();

        System.out.println("Módulo A vivo:  " + moduloA.isAlive());
        System.out. println("Módulo B vivo: " + moduloB.isAlive());
        System.out.println("Módulo C vivo: " + moduloC. isAlive());

        try {
            Thread.sleep(1500);
            moduloB.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            moduloA.join();
            System.out.println("Módulo A finalizado. Estado vivo: " + moduloA.isAlive());
            
            moduloB.join();
            System.out.println("Módulo B finalizado. Estado vivo: " + moduloB. isAlive());
            
            moduloC.join();
            System.out.println("Módulo C finalizado. Estado vivo: " + moduloC.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(moduloA.toString());
        System.out.println(moduloB.toString());
        System.out.println(moduloC. toString());

        /*
        Las prioridades sugieren orden pero no lo garantizan, el SO decide la planificación real. 
        yield() permite que otros hilos ejecuten pero su efecto puede ser imperceptible. 
        La interrupción mediante interrupt() es la forma segura de detener un hilo cooperativamente.
        El comportamiento observado depende del número de núcleos y la carga del sistema operativo.
        */
    }
}