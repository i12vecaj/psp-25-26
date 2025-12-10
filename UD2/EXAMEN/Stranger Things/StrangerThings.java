
/**
 * Lo que he hecho es que Eleven/Once genere eventos y el laboratorio al procesarlos los borra.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class StrangerThings {

    public static void main(String[] args) {
        System.out.println("Bienvenido a Hawkins");

        int capacidadBuffer = 5;
        int totalEventos = 20;

        Hawkins hawkins = new Hawkins(capacidadBuffer);

        Eleven eleven = new Eleven(hawkins, totalEventos);
        Laboratorio laboratorio = new Laboratorio(hawkins, totalEventos);

        Thread elevenHilo = new Thread(eleven, "Eleven");
        Thread laboratorioHilo = new Thread(laboratorio, "Laboratorio");

        elevenHilo.start();
        laboratorioHilo.start();

        try {
            elevenHilo.join();
            laboratorioHilo.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El programa fue interrumpido.");
        }

        System.out.println("Programa finalizado. Total de eventos: " + totalEventos);
    }

}

class Laboratorio implements Runnable {
    private final Hawkins hawkins;
    private final int eventosAProcesar;

    public Laboratorio(Hawkins hawkins, int eventosAProcesar) {
        this.hawkins = hawkins;
        this.eventosAProcesar = eventosAProcesar;
    }

    @Override
    public void run() {
        for (int i = 0; i < eventosAProcesar; i++) {
            try {
                Evento evento = hawkins.eliminarEvento();
                System.out.println("Laboratorio procesa evento: " + evento.getId());
                // Darle tiempo para "procesar" el evento
                Thread.sleep(new Random().nextInt(300) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Laboratorio interrumpido mientras procesaba un evento.");
                break;
            }
        }
        System.out.println("Laboratorio ha procesado " + eventosAProcesar + " eventos.");
    }
}

class Evento {
    private final int id;
    private static int contador = 0;
    // private boolean estado;

    public Evento() {
        this.id = contador++;
        // this.estado = false;
    }

    public int getId() {
        return id;
    }

    /*
     * public boolean getEstado() {
     * return estado;
     * }
     * 
     * public void setEstado(boolean estado) {
     * this.estado = estado;
     * }
     */
}

class Eleven implements Runnable {
    private final Hawkins hawkins;
    private final int eventosAGenerar;

    public Eleven(Hawkins hawkins, int eventosAGenerar) {
        this.hawkins = hawkins;
        this.eventosAGenerar = eventosAGenerar;
    }

    @Override
    public void run() {
        for (int i = 0; i < eventosAGenerar; i++) {
            try {
                Evento nuevoEvento = new Evento();
                System.out.println("Once ha generado un evento: " + nuevoEvento.getId());
                hawkins.agregarEvento(nuevoEvento);
                // Tiempo para generar el siguiente evento
                Thread.sleep(new Random().nextInt(200) + 50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Programa interrumpido");
                break;
            }
        }
        System.out.println("Once ha generado " + eventosAGenerar + " eventos en total.");
    }
}

// Hawkins va a ser nuestro buffer
class Hawkins {
    private final int capacidadMax;
    private List<Evento> eventos;

    public Hawkins(int capacidadMax) {
        this.capacidadMax = capacidadMax;
        this.eventos = new ArrayList<>();
    }

    public synchronized void agregarEvento(Evento evento) throws InterruptedException {
        while (eventos.size() >= capacidadMax) {
            System.out.println("Buffer de eventos lleno, Once esperando...");
            wait();
        }

        eventos.add(evento);
        System.out.println("Evento agregado: " + evento.getId() + " (Eventos en curso: " + eventos.size() + ")");
        notifyAll();
    }

    public synchronized Evento eliminarEvento() throws InterruptedException {
        while (eventos.isEmpty()) {
            System.out.println("Buffer de eventos vacío, Laboratorio esperando...");
            wait();
        }

        Evento evento = eventos.removeFirst(); // Se borra el evento más antiguo
        System.out.println("Evento eliminado: " + evento.getId() + " (Tamaño actual: " + eventos.size() + ")");
        notifyAll();
        return evento;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }
}