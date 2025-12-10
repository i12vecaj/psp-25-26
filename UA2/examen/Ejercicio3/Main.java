/*

Sobre la priorización aunque yo le ponga que tenga X prioridad esto sigue sin realmente elegir el orden y lo decide más el sistema
operativo que este sabe como controlarlo mejor cambiando la prioridad a las que el ve correctas aunque yo le imponga una,
sobre yield me he fijado que a veces, pero muy rara vez funciona, ya que es una recomendación más que una obligación y
sobre la interrupción ha hecho que el hilo deje de dormir instantáneamente mostrando el mensaje de error por interrupción.

 */
public class Main {
    static void main(String[] args){

        ModuloTrabajo mt1 = new ModuloTrabajo("Módulo A");
        ModuloTrabajo mt2 = new ModuloTrabajo("Módulo B");
        ModuloTrabajo mt3 = new ModuloTrabajo("Módulo C");

        mt1.setPriority(Thread.MAX_PRIORITY);
        mt2.setPriority(Thread.NORM_PRIORITY);
        mt3.setPriority(Thread.MIN_PRIORITY);


        mt1.start();
        mt2.start();
        mt3.start();

        mt2.interrupt();


        System.out.println("¿Esta vivo el hilo 1?: "+ mt1.isAlive());
        System.out.println("¿Esta vivo el hilo 2?: "+ mt2.isAlive());
        System.out.println("¿Esta vivo el hilo 3?: "+ mt3.isAlive());



        try {
            mt1.join();
            System.out.println("Modulo "+mt1.getNombreModulo()+ " finalizado. Estado vivo: " + mt1.isAlive());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            mt2.join();
            System.out.println("Modulo "+mt2.getNombreModulo()+ " finalizado. Estado vivo: " + mt2.isAlive());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            mt3.join();
            System.out.println("Modulo "+mt3.getNombreModulo()+ " finalizado. Estado vivo: " + mt3.isAlive());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(mt1.toString());
        System.out.println(mt2.toString());
        System.out.println(mt3.toString());

    }
}
