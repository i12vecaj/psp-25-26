import java.util.ArrayList;
import java.util.List;

public class Laboratorio {

    private final String nombre;
    private final int capacidadMaxima;
    private final List<Evento> Eventos;
    private int totalProducidas = 0;
    private int totalConsumidas = 0;

    public Laboratorio(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidad;
        this.Eventos = new ArrayList<>();
    }

    public synchronized void producirEvento(Evento evento) {
        while (Eventos.size() >= capacidadMaxima) {
            try {
                System.out.println(evento.getEleven() + " esperando");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Eventos.add(evento);
        totalProducidas++;

        System.out.println("==================================================");
        System.out.println(evento.getEleven() + " ha producido:");
        System.out.println("   " + evento);
        mostrarEstado();

        notifyAll();
    }

    public synchronized Evento consumirEvento(String nombreCliente) {
        while (Eventos.isEmpty()) {
            try {
                System.out.println(nombreCliente + " esperando");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Evento evento = Eventos.remove(0);
        totalConsumidas++;

        System.out.println("==================================================");
        System.out.println(nombreCliente + " ha procesado:");
        System.out.println("   " + evento);
        System.out.println("==================================================");
        mostrarEstado();

        notifyAll();
        return evento;
    }

    private void mostrarEstado() {
        System.out.println("Estado del Laboratorio:");
        System.out.println(Eventos.size() + "/" + capacidadMaxima);
        System.out.println(totalProducidas + " | Realizados: " + totalConsumidas);
        System.out.println("Balance: " + (totalProducidas - totalConsumidas));
        System.out.println("==================================================\n");
    }
}
