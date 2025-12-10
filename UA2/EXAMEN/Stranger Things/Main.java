public class Main {
    private static final int BUFFER_SIZE = 5;
    private static final int TOTAL_EVENTS = 20;

    public static void main(String[] args) {
        BufferCompartido sharedBuffer = new BufferCompartido(BUFFER_SIZE);

        Productor eleven = new Productor(sharedBuffer, TOTAL_EVENTS);
        Consumidor hawkinsLab = new Consumidor(sharedBuffer, TOTAL_EVENTS);

        System.out.println("Iniciando Operación Stranger Things");

        eleven.start();
        hawkinsLab.start();

        try {
            System.out.println("Hilo principal durmiendo");
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Operación Stranger Things Finalizada");
    }
}