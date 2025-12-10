public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("TechFactory");

        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");
        

        moduloA.setPriority(Thread.MAX_PRIORITY);   
        moduloB.setPriority(Thread.NORM_PRIORITY); 
        moduloC.setPriority(Thread.MIN_PRIORITY);

        System.out.println("Inicio:");
        System.out.println(moduloA);
        System.out.println(moduloB);
        System.out.println(moduloC);
        System.out.println("");

        moduloA.start();
        moduloB.start();
        moduloC.start();

        System.out.println("Estado inicial:");
        System.out.println("Módulo A vivo: " + moduloA.isAlive());
        System.out.println("Módulo B vivo: " + moduloB.isAlive());
        System.out.println("Módulo C vivo: " + moduloC.isAlive());
        System.out.println("");

        Thread.sleep(1500);
        System.out.println("Interrumpiendo B");
        moduloB.interrupt();

        try {
            moduloA.join();
            System.out.println("A finalizado. Vivo?: " + moduloA.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            moduloB.join();
            System.out.println("B finalizado. Vivo?: " + moduloB.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            moduloC.join();
            System.out.println("C finalizado. Vivo?: " + moduloC.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("");

        System.out.println("final de los módulos");
        System.out.println(moduloA);
        System.out.println(moduloB);
        System.out.println(moduloC);

/*
-Aunque se asignaron prioridades diferentes la JVM y el sistema operativo no garantizan que se respete
- Al llamar a yield el hilo sugiere ceder la CPU a otros hilos de igual prioridad pero el planificador 
puede ignorarlo segun como de cargado esté el sistema.
- Al interrumpir B el hilo finaliza inmediatamente al capturar la excepción sin completar todas sus
 iteraciones, esto es totalmente correcto.
- El sistema operativo gestiona la CPU de los hilos y puede que se observe un comportamiento
 diferente dependiendo de la máquina

*/
        System.out.println("\n Y chimpun");
    }
}
