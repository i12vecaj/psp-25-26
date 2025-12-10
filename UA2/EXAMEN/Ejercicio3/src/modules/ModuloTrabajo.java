package modules;

public class ModuloTrabajo extends Thread {

    private String nombre;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado. ID: " + this.getId());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Módulo " + nombre + " – iteración " + i);

            try {
                int tiempo = (int) (Math.random() * 501) + 300; // Sleep entre 300 y 800 ms
                Thread.sleep(tiempo);

                // Llama a yield() en la tercera iteración
                if (i == 3) {
                    System.out.println("Módulo " + nombre + " hace yield()");
                    Thread.yield();
                }

            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "ModuloTrabajo{nombre='" + nombre +
                "', id=" + this.getId() +
                ", prioridad=" + this.getPriority() + "}";
    }
}

