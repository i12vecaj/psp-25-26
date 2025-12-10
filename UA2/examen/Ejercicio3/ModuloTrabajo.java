public class ModuloTrabajo extends Thread {

    private String nombreModulo;

    public ModuloTrabajo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombreModulo + " iniciado. ID: " + this.getId());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Módulo " + nombreModulo + " – iteración " + i);

            if (i == 3) {
                Thread.yield();
            }

            int aleatorio = (int) (300 + Math.random() * 500); //aleatorio para el sleep

            try {
                Thread.sleep(aleatorio);
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombreModulo + " interrumpido durante la ejecución");
                return;
            }
        }
    }


    @Override
    public String toString() {
        return "ModuloTrabajo{nombre='" + nombreModulo + "', id=" + this.getId() +
                ", prioridad=" + this.getPriority() + "}";
    }
}