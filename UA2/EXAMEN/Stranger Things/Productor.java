import java.util.Random;

public class Productor extends Thread {
    private final BufferCompartido buffer;
    private final int numEventos;
    private int eventosProducidos = 0;
    private final String[] tiposEvento = {"Demogorgon detectado", "Portal inestable", "Criatura vista", "Falta energia"};
    private final Random random = new Random();

    public Productor(BufferCompartido buffer, int numEventos) {
        this.buffer = buffer;
        this.numEventos = numEventos;
    }

    @Override
    public void run() {
        try {
            while (eventosProducidos < numEventos) {
                String eventoBase = tiposEvento[random.nextInt(tiposEvento.length)];
                String eventoId = (eventosProducidos + 1) + " (" + eventoBase + ")";

                buffer.put(eventoId);
                eventosProducidos++;

                Thread.sleep(random.nextInt(401) + 100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Finalizando productor");
    }
}