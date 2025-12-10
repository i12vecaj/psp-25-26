import java.util.Random;

public class ModuloTrabajo extends Thread {
    private final String nombre;
    private final Random random = new Random();
    private static final int NUM_ITERACIONES = 5;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado");

        for (int i = 1; i <= NUM_ITERACIONES; i++) {
            System.out.println("Módulo " + nombre + " interacción" + i);

            if (i == 3) {
                Thread.yield();
            }

            try {
                Thread.sleep(random.nextInt(501) + 300);
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Módulo Trabajo " + nombre + " id=" + getId();
    }
}