

//En cuanto a priorización de hilos he observado que incluso dandole prioridad depende del planificador de tareas la prioridad de estos
// En el método yield() he observado que como lo he usado desde la clase módulo de trabajo y extiende de Thread he tenido que usar Thread. primero. También he observado que depende del planificador de taeas de que se ceda el turno o no
//En la interrupción he observado que como no se duerme ningún hilo en el main no se captura la interrupción
//La planificación del sistema operativo he visto que se fija en el procesador para decidir la prioridad, etc.
public class Main {
    public static void main(String[] args) {

        ModuloTrabajo hilo1 = new ModuloTrabajo(1, 10, "Finanzas");
        ModuloTrabajo hilo2 = new ModuloTrabajo(2, 4, "Informática");
        ModuloTrabajo hilo3 = new ModuloTrabajo(3, 5, "Marketing");

        // Se crean los hilos (no extienden Thread directamente)
        Thread t1 = new Thread(hilo1);
        Thread t2 = new Thread(hilo2);
        Thread t3 = new Thread(hilo3);


        t1.setPriority(Thread.MAX_PRIORITY); //Le asigno a primer hilo la prioridad máxima para que tenga prioridad alta
        t2.setPriority(Thread.NORM_PRIORITY); //Le asigno al segundo hilo la prioridad media para que tenga prioridad media
        t3.setPriority(Thread.MIN_PRIORITY); // Le asigno a tercer hilo la míninma prioridad para que tenga prioridad baja

        // Arranco los hilos
        t1.start();
        t2.start();
        t3.start();

        System.out.println(t1.isAlive());
        System.out.println(t2.isAlive());
        System.out.println(t3.isAlive());

        try {
            t1.join();
            t2.join();
            t3.join();

        }
        catch (InterruptedException e)
        {
            // Nothing to do here ...
        }

        System.out.println("Todos los hilos han terminado");

        System.out.println(hilo1.toString());
        System.out.println(hilo2.toString());
        System.out.println(hilo3.toString());
    }



}




