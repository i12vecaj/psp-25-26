public class CarreraDeHilos {
    public static void main(String[] args) {
        // Creamos tres hilos con distintas prioridades
        MiHilo h1 = new MiHilo("Hilo-1");
        MiHilo h2 = new MiHilo("Hilo-2");
        MiHilo h3 = new MiHilo("Hilo-3");

        h1.setPriority(Thread.MIN_PRIORITY);   // Prioridad más baja
        h2.setPriority(Thread.NORM_PRIORITY);  // Prioridad normal
        h3.setPriority(Thread.MAX_PRIORITY);   // Prioridad más alta

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