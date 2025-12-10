import java.util.Random;

public class Consumidor extends Thread {
    private final BufferCompartido buffer;
    private final int numEventos;
    private int eventosConsumidos = 0;
    private final Random random = new Random();

    public Consumidor(BufferCompartido buffer, int numEventos) {
        this.buffer = buffer;
        this.numEventos = numEventos;
    }

    @Override
    public void run() {
        try {
            while (eventosConsumidos < numEventos) {
                buffer.get();
                eventosConsumidos++;

                Thread.sleep(random.nextInt(401) + 300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Finalizando Consumidor.");
    }
}