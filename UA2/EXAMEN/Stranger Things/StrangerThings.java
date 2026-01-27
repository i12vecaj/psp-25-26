
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class Buffer {
    private List<String> cola;
    private int size;

    public Buffer(int size) {
        this.cola = new LinkedList<>();
        this.size = size;
    }

    public synchronized void produce(String event) throws InterruptedException {
        while (cola.size() == size) {
            wait();
        }
        cola.add(event);
        System.out.println("Eleven genera evento: " + event);
        notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (cola.isEmpty()) {
            wait();
        }
        String event = cola.remove(0);
        System.out.println("Laboratorio procesa evento: " + event);
        notifyAll();
        return event;
    }
}

class Productor implements Runnable {
    private Buffer buffer;
    private int totalEvents;

    public Productor(Buffer buffer, int totalEvents) {
        this.buffer = buffer;
        this.totalEvents = totalEvents;
    }

    @Override
    public void run() {
        String[] events = { "Demogorgon detectado", "Portal inestable", "Portal Abierto", "Vecna se Acerca",
        };
        Random random = new Random();
        for (int i = 0; i < totalEvents; i++) {
            try {
                String event = events[random.nextInt(events.length)];
                buffer.produce(event);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumidor implements Runnable {
    private Buffer buffer;
    private int totalEvents;

    public Consumidor(Buffer buffer, int totalEvents) {
        this.buffer = buffer;
        this.totalEvents = totalEvents;
    }

    @Override
    public void run() {
        for (int i = 0; i < totalEvents; i++) {
            try {
                buffer.consume();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class StrangerThings {
    public static void main(String[] args) {
        final int BUFFER_SIZE = 5;
        final int TOTAL_EVENTS = 20;

        Buffer buffer = new Buffer(BUFFER_SIZE);
        Thread productor = new Thread(new Productor(buffer, TOTAL_EVENTS));
        Thread consumidor = new Thread(new Consumidor(buffer, TOTAL_EVENTS));

        productor.start();
        consumidor.start();
    }
}