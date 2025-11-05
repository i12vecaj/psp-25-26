/**
 Cuando pones start se ejecutan todos los hilos a la vez y en un orden aleatorio(aunque aquí hay prioridades), pero si pones un 
 run se ejecutan de uno es uno porque el hilo que está activo tiene que terminar para que se ejecute el siguiente.


 Si se cambia la prioridad de los hilos va a influir en el orden de ejecución de los hilos.


 Con el interrupt del hilo 2 indica que se interrumpe y no termina su ejecución, y esto hace que pase el siguiente.


 Pasa a false si los hilos ya no están vivos.

 
 Si metes diferentes imprimir h1.toString() la información en la consola se va actualizando según el estado del hilo en ese momento.
 */

public class ejercicioSlack {
    public static void main(String[] args) {
        // Creamos tres hilos con distintas prioridades
        MiHilo h1 = new MiHilo("Hilo-1");
        MiHilo h2 = new MiHilo("Hilo-2");
        MiHilo h3 = new MiHilo("Hilo-3");

        h1.setPriority(Thread.MAX_PRIORITY);   // Prioridad más baja
        h2.setPriority(Thread.NORM_PRIORITY);  // Prioridad normal
        h3.setPriority(Thread.MIN_PRIORITY);   // Prioridad más alta

        System.out.println("🔹 Estado inicial de los hilos:");
        System.out.println(h1.toString());
        System.out.println(h2.toString());
        System.out.println(h3.toString());

        // Iniciamos los hilos
        h1.start();
        h2.start();
        h3.start();

        // Interrumpimos el hilo 2 tras un pequeño retraso
        try {
            System.out.println(h1.toString());
            Thread.sleep(10);
            h2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Comprobamos si los hilos siguen vivos
        System.out.println("\n🔹 ¿Siguen vivos?");
        System.out.println(h1.getName() + ": " + h1.isAlive());
        System.out.println(h2.getName() + ": " + h2.isAlive());
        System.out.println(h3.getName() + ": " + h3.isAlive());

        System.out.println(h1.toString());
    }
}

class MiHilo extends Thread {
    public MiHilo(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        System.out.println("▶️ Iniciando " + getName() + " (ID: " + getId() + ", prioridad: " + getPriority() + ")");
        for (int i = 1; i <= 5; i++) {
            System.out.println(getName() + " -> " + i);
            try {
                // Simulamos un pequeño descanso y uso de yield()
                if (i == 3) {
                    System.out.println(getName() + " cede el turno (yield)");
                    Thread.yield();
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("❌ " + getName() + " fue interrumpido.");
                return;
            }
        }
        System.out.println("🏁 " + getName() + " ha terminado.");
    }
}