public class Tarea implements Runnable {
    private final String nombreTarea;

    public Tarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    @Override
    public void run() {
        long idHilo = Thread.currentThread().getId();

        System.out.println("[" + nombreTarea + "] ejecutándose en el hilo " + idHilo);

        try {
            int tiempoDormir = 500 + (int) (Math.random() * 1001);

            Thread.sleep(tiempoDormir);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + nombreTarea + "] interrumpida.");
        }

        System.out.println("[" + nombreTarea + "] finalizada." + idHilo);
    }
}