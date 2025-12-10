import java.util.Random;

public class ModuloTrabajo extends Thread {
    private final long id;
    private final String nombreModulo;
    private static long contador = 0;

    public ModuloTrabajo(String nombreModulo) {
        this.id = contador++;
        this.nombreModulo = nombreModulo;
    }

    public long getID() {
        return id;
    }

    @Override
    public String toString() {
        return "MóduloTrabajo [nombre=" + nombreModulo + ", id=" + getID() + ", prioridad=" + getPriority() + "]";
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombreModulo + " iniciado.");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Módulo " + nombreModulo + " iteracion: " + (i + 1));
                Thread.sleep(new Random().nextInt(800 - 300 + 1) + 300);
                if (i == 2) { // Tercera iteración
                    System.out.println("Módulo " + nombreModulo + " haciendo yield.");
                    Thread.yield();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Módulo " + nombreModulo + " interrumpido durante la ejecución.");
            return;
        }
        System.out.println("Módulo " + nombreModulo + " finalizado. Estado: " + isAlive());
    }

}
