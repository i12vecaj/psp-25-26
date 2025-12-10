import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        int totalEvents = 10;

        Productor productor = new Productor(buffer, totalEvents);
        Consumidor consumidor = new Consumidor(buffer, totalEvents);

        productor.start();
        consumidor.start();

        try {
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Todos los eventos han sido procesados.");
    }
}

class Buffer {
    private final List<String> buffer;
    private final int maxSize;

    public Buffer(int size) {
        this.buffer = new LinkedList<>();
        this.maxSize = size;
    }

    public synchronized void produce(String event) throws InterruptedException {
        while (buffer.size() == maxSize) {
            System.out.println("Buffer lleno, Eleven espera...");
            wait();
        }
        buffer.add(event);
        System.out.println("Eleven genera un evento: " + event);
        notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("Buffer vacío, Laboratorio espera...");
            wait();
        }
        String event = buffer.remove(0);
        System.out.println("Laboratorio procesa un evento: " + event);
        notifyAll();
        return event;
    }
}

class Productor extends Thread {
    private final Buffer buffer;
    private final int eventosToProduce;
    private final Random random = new Random();
    private final String[] eventos = {
            "Demogorgon detectado",
            "Portal inestable",
            "Criatura del Upside Down avistada",
            "Anomalía temporal detectada",
            "Energía extraña capturada"
    };

    public Productor(Buffer buffer, int eventosToProduce) {
        this.buffer = buffer;
        this.eventosToProduce = eventosToProduce;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < eventosToProduce; i++) {
                String evento = eventos[random.nextInt(eventos.length)];
                buffer.produce(evento);
                Thread.sleep(random.nextInt(1000)); // Simula tiempo de producción
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumidor extends Thread {
    private final Buffer buffer;
    private final int eventosToConsume;

    public Consumidor(Buffer buffer, int eventosToConsume) {
        this.buffer = buffer;
        this.eventosToConsume = eventosToConsume;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < eventosToConsume; i++) {
                buffer.consume();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}