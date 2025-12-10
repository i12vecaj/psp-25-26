public class Main {
    public static void main(String[] args){
        ModuloTrabajo modulo1 = new ModuloTrabajo("A");
        ModuloTrabajo modulo2 = new ModuloTrabajo("B");
        ModuloTrabajo modulo3 = new ModuloTrabajo("C");

        Thread hilo1 = new Thread(modulo1);
        hilo1.setPriority(10);
        Thread hilo2 = new Thread(modulo2);
        hilo2.setPriority(5);
        Thread hilo3 = new Thread(modulo3);
        hilo3.setPriority(1);

        hilo1.start();
        hilo2.start();
        hilo3.start();
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            System.out.println("Modulo " + modulo1.getModulo() + " finalizado. Estado vivo: " + hilo1.isAlive());
            System.out.println("Modulo " + modulo2.getModulo() + " finalizado. Estado vivo: " + hilo2.isAlive());
            System.out.println("Modulo " + modulo3.getModulo() + " finalizado. Estado vivo: " + hilo3.isAlive());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(modulo1);
        System.out.println(modulo2);
        System.out.println(modulo3);

    }
}

// Explica brevemente (4–5 líneas, dentro del código como comentario) qué has observado respecto a:
//
//priorización de hilos,
//
//yield(),
//
//interrupción,
//
//planificación del sistema operativo.

/*He podido observar que al asignar la prioridad a los hilos se ejecutan segun esa prioridad o deberian pero
 creo que debido a mi sistema operativo no estan siguiendo el orden que deberian.
 He visto tambien que al llamar al yield() el orden de ejecucion cambia debido a que ese hilo ha cedido su ejecucion
 al siguiente. 
 */

