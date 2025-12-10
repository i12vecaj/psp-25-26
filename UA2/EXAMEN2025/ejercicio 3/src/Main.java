/*
AUTOR: Antonio Rodríguez Cortés
FECHA: 10/12/2025
Clase: 2 DAM
*/

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY); // PRIORIDAD ALTA
        moduloB.setPriority(Thread.NORM_PRIORITY); // PRIORIDAD NORMAL
        moduloC.setPriority(Thread.MIN_PRIORITY); // PRIORIDAD BAJA

        // INICIAR HILOS
        moduloA.start();
        moduloB.start();
        moduloC.start();

        // COMPROBAMOS SI ESTÁN VIVOS
        System.out.println("Estoy vivo soy: A " + moduloA.isAlive());
        System.out.println("Estoy vivo soy: B " + moduloB.isAlive());
        System.out.println("Estoy vivo soy: C " + moduloC.isAlive());

        Thread.sleep(1500);
        moduloB.interrupt();

        // ESPERAR A QUE TERMINEN LOS HILOS GRACIAS A JOIN
        moduloA.join();
        System.out.println("Módulo A finalizado. Estado vivo: " + moduloA.isAlive());
        moduloB.join();
        System.out.println("Módulo B finalizado. Estado vivo: " + moduloB.isAlive());
        moduloC.join();
        System.out.println("Módulo C finalizado. Estado vivo: " + moduloC.isAlive());

        System.out.println(moduloA.toString());
        System.out.println(moduloB.toString());
        System.out.println(moduloC.toString());
    }
}

// Lo primero que he notado es que los hilos con diferentes prioridades no tiene garantizado que su orden sea respetado.
// Luego el metodo yield() puede hacer que un hilo ceda el turno, pero no asegura cuando dara su turno de nuevo.
// La interrupción sirve para detener un hilo concreto, en este caso el hilo B.
// La planificación afecta segun el sistema operativo, entiendo que en mi caso (Mac) debe ir con otra planificación distinta a Windows.
