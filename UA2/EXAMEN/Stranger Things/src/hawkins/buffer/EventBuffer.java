package hawkins.buffer;

import hawkins.modelo.Evento;

import java.util.LinkedList;
import java.util.List;

public class EventBuffer {

    private final List<Evento> buffer;
    private final int capacidad;

    public EventBuffer(int capacidad) {
        this.capacidad = capacidad;
        this.buffer = new LinkedList<>();
    }

    // Añadir evento: productor
    public synchronized void producir(Evento evento) throws InterruptedException {
        while (buffer.size() == capacidad) {
            System.out.println("BUFFER lleno. Eleven espera...");
            wait();  // espera hasta que haya hueco
        }

        buffer.add(evento);  // añade al final (equivalente a enqueue)
        System.out.println("Eleven genera un nuevo evento:  " + evento.getDescripcion());
        notifyAll();  // despierta al consumidor
    }

    // Consumir evento: consumidor
    public synchronized Evento consumir() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("BUFFER vacío. Laboratorio espera...");
            wait();  // espera hasta que haya algún evento
        }

        Evento e = buffer.remove(0);
        System.out.println("Laboratorio procesa:  " + e.getDescripcion());
        notifyAll();  // despierta al productor

        return e;
    }
}
