// FR1

import java.util.Random;

class ModuloTrabajo extends Thread {
    private final String nombre;
    private final Random r = new Random();

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado. ID: " + getId());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Módulo " + nombre + " – iteración " + i);

            if (i == 3) {
                Thread.yield();
            }

            try {
                Thread.sleep(300 + r.nextInt(501));
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "ModuloTrabajo{Nombre='" + nombre + " , id=" + getId() + " , prioridad=" + getPriority() + "}";
    }
}

