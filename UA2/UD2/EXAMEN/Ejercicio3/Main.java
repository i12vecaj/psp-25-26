/**
 * He observado que la priorización da igual porque unas veces termina uno antes
 * que otro
 * aún haciendo yield y cediendo procesamiento, es completamente aleatorio.
 *
 * Al interrumpir el moduloB simplemente desaparece pero si intento su
 * interrupción después de hacer .join
 * jamás se interrumpirá
 */

public class Main {
    public static void main(String[] args) {

        System.out.println("Ejercicio 3\n");

        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY);
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread.MIN_PRIORITY);

        moduloA.start();
        moduloB.start();
        moduloC.start();

        try {
            Thread.sleep(1500);
            moduloB.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo principal interrumpido intentando interrumpir el módulo B.");
        }

        try {
            moduloA.join();
            moduloB.join();
            moduloC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // si ponemos aquí el try para intentar interrumpir el módulo B NUNCA se va a
        // dormir

        System.out.println("Módulo A Estado: " + moduloA.isAlive());
        System.out.println("Módulo B Estado: " + moduloB.isAlive());
        System.out.println("Módulo C Estado: " + moduloC.isAlive());

        System.out.println(moduloA.toString());
        System.out.println(moduloB.toString());
        System.out.println(moduloC.toString());

    }
}
