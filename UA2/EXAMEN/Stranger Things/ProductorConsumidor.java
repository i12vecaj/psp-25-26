/*
 * Nombre: Pablo Herrador Castillo
 * Fecha: 10/12/2025
 * Descripción: Productor / Consumidor – “Stranger Things”
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProductorConsumidor {
    public static void main(String[] args) throws InterruptedException {
        SharedBuffer buffer = new SharedBuffer(5);
        int totalEvents = 20;

        Eleven producer = new Eleven(buffer, totalEvents);
        Laboratorio consumer = new Laboratorio(buffer, totalEvents);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Todos los eventos producidos y consumidos. Programa finalizado.");
    }
}
    
    class SharedBuffer {
    private final Queue<String> buffer = new LinkedList<>();
    private final int capacity;

    SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(String event) throws InterruptedException {
        while (buffer.size() == capacity) {
            System.out.println("[Eleven] Buffer lleno, productor espera.");
            wait();
        }
        buffer.add(event);
        System.out.println("[Eleven] Genera evento: " + event + " (tam=" + buffer.size() + ")");
        notifyAll();
    }
    
    public synchronized String take() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("[Laboratorio] Buffer vacío, consumidor espera.");
            wait();
        }
        String event = buffer.remove();
        System.out.println("[Laboratorio] Procesa evento: " + event + " (tam=" + buffer.size() + ")");
        notifyAll();
        return event;
    }
}

class Eleven extends Thread {
    private final SharedBuffer buffer;
    private final int totalToProduce;
    private final Random rnd = new Random();
    private static final String[] EVENTS = {
        "Demogorgon detectado",
        "Portal inestable",
        "Ecos sensoriales",
        "Actividad psíquica detectada",
        "Entidades en movimiento"
    };
   Eleven(SharedBuffer buffer, int totalToProduce) {
        this.buffer = buffer;
        this.totalToProduce = totalToProduce;
        setName("Eleven-Productor");
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= totalToProduce; i++) {
                String event = "#" + i + " - " + EVENTS[rnd.nextInt(EVENTS.length)];
                buffer.put(event);
                Thread.sleep(50 + rnd.nextInt(200));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
   class Laboratorio extends Thread {
    private final SharedBuffer buffer;
    private final int totalToConsume;

    Laboratorio(SharedBuffer buffer, int totalToConsume) {
        this.buffer = buffer;
        this.totalToConsume = totalToConsume;
        setName("Laboratorio-Consumidor");
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= totalToConsume; i++) {
                String event = buffer.take();
                
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
