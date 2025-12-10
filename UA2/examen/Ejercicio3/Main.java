public class Main {
    public static void main(String[] args) throws InterruptedException {

        ModuloTrabajo mt1 = new ModuloTrabajo("A");
        ModuloTrabajo mt2 = new ModuloTrabajo("B");
        ModuloTrabajo mt3 = new ModuloTrabajo("C");

        mt1.setPriority(Thread.MAX_PRIORITY);      // Alta
        mt2.setPriority(Thread.NORM_PRIORITY);     // Media
        mt3.setPriority(Thread.MIN_PRIORITY);      // Baja


        mt1.start();
        mt2.start();
        mt3.start();


        System.out.println("¿mtA vivo?: " + mt1.isAlive());
        System.out.println("¿mtB vivo?: " + mt2.isAlive());
        System.out.println("¿mtC vivo?: " + mt3.isAlive());

        Thread.sleep(1500);
        mt2.interrupt();


        mt1.join();
        System.out.println("Módulo A finalizado. Estado vivo: " + mt1.isAlive());

        mt2.join();
        System.out.println("Módulo B finalizado. Estado vivo: " + mt2.isAlive());

        mt3.join();
        System.out.println("Módulo C finalizado. Estado vivo: " + mt3.isAlive());


        System.out.println("\n--- Información final de módulos ---");
        System.out.println(mt1);
        System.out.println(mt2);
        System.out.println(mt3);

        //Explica brevemente (4–5 líneas, dentro del código como comentario) qué has observado respecto a:
        //priorización de hilos,
        //yield(),
        //interrupción,
        //planificación del sistema operativo.
        //La priorización de hilos se tiene en cuenta a la hora de gestionar los hilos y hacer los prints aunque no esta asegurado,
        //vemos como el yield cede recursos a otros hilos en la tercera iteración
        //Al interrumpirse no llega a cumplir su funcion antes de morir
        //Nosotros podemos manipular los recursos del S.O. mediante el planificador y gestionar como mejor nos convenga prioridades


    }
}
