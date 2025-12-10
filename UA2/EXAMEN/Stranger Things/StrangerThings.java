import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class StrangerThings {
    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 5;
        int totalEvents = 20;
        Buffer buffer = new Buffer(bufferSize);

        Thread productor = new Productor(buffer, totalEvents);
        Thread consumidor = new Consumidor(buffer, totalEvents);

        productor.start();
        consumidor.start();

        productor.join();
        consumidor.join();

        System.out.println("Fin");
    }
}

class Buffer {
    private final Queue<String> queue = new ArrayDeque<>();
    private final int capacidad;

    Buffer(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void put(String event) throws InterruptedException {
        while (queue.size() == capacidad) {
            System.out.println("Esperas por buffer lleno");
            wait();
        }
        queue.add(event);
        System.out.println(
                "Eleven genera un evento: " + event + " (buffer: " + queue.size() + "/" + capacidad + ")");
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("Esperas por buffer vacio");
            wait();
        }
        String event = queue.remove();
        System.out.println(
                "Laboratorio procesa un evento: " + event + " (buffer: " + queue.size() + "/" + capacidad + ")");
        notifyAll();
        return event;
    }
}

class Productor extends Thread {
    private static final String[] EVENTS = {
            "Demogorgon detectado",
            "Portal inestable",
            "No sé, me quedé en la primera temporada",
            "Pongo otro ejemplo sin tener mucha idea de la serie, por si acaso"
    };

    private final Buffer buffer;
    private final int totalEvents;
    private final Random random = new Random();

    Productor(Buffer buffer, int totalEvents) {
        this.buffer = buffer;
        this.totalEvents = totalEvents;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= totalEvents; i++) {
                String event = EVENTS[random.nextInt(EVENTS.length)] + " #" + i;
                buffer.put(event);
                Thread.sleep(120 + random.nextInt(180));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumidor extends Thread {
    private final Buffer buffer;
    private final int totalEvents;
    private final Random random = new Random();

    Consumidor(Buffer buffer, int totalEvents) {
        this.buffer = buffer;
        this.totalEvents = totalEvents;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalEvents; i++) {
                buffer.take();
                Thread.sleep(150 + random.nextInt(200));
            }
            System.out.println("Total: " + totalEvents + " eventos.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
