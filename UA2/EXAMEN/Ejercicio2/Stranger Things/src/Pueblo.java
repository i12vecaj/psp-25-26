import java.util.ArrayList;
import java.util.List;

public class Pueblo {
private final String nombre;
    private final int capacidadMaximaBuffer;
    private final List<Eventos> listaEventos;
    private int totalProducidas = 0;
    private int totalConsumidas = 0;

    public Pueblo(String nombre, int numeroEventos) {
        this.nombre = nombre;
        this.capacidadMaximaBuffer = numeroEventos;
        this.listaEventos = new ArrayList<>();
        System.out.println("Evento: " + nombre + " acaba de suceder en el pueblo.");
        System.out.println("Cantidad de eventos sin investigar: " + numeroEventos);
        System.out.println("==================================================\n");

}
 public synchronized void producirEvento(Eventos evento) {
        // Si la lista de eventos no está vacia, Eleven debe esperar
        while (listaEventos.size() >= capacidadMaximaBuffer) {
            try {
                System.out.println(evento.getProductor() + " esperando para producir.");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Colocar el evento en la lista
        listaEventos.add(evento);
        totalProducidas++;

        System.out.println("==================================================");
        System.out.println(evento.getProductor() + " ha creado el siguiente evento:");
        System.out.println("   " + evento);
        mostrarEstado();

        if (listaEventos.size() == 5) {
            notifyAll();
        }

        if(totalProducidas == 20){
            System.out.println("Se ha alcanzado el límite de eventos en el pueblo. Fin de la simulación.");
            System.exit(0);
        }
    
}
public synchronized Eventos consumirEvento(String nombreLaboratorio) {
        // Si la lista de eventos está vacía, el laboratorio debe esperar
        while (listaEventos.isEmpty()) {
            try {
                System.out.println(nombreLaboratorio + " esperando.");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Tomar el primer evento de la lista
        Eventos evento = listaEventos.remove(0);
        totalConsumidas++;

        System.out.println("==================================================");
        System.out.println(nombreLaboratorio + " ha investigado:");
        System.out.println("   " + evento);
        mostrarEstado();

        // Notificar a Eleven que hay espacio en la lista
        if (listaEventos.size() == 0) {
        notifyAll();
        }
        return evento;
    }

    private void mostrarEstado() {
        System.out.println("Estado actual del pueblo " + nombre + ":");
        System.out.println(" Lista de eventos sin investigar: " +listaEventos.size() + "/" + capacidadMaximaBuffer);
        System.out.println(" Eventos producidos: " + totalProducidas + " | Consumidas: " + totalConsumidas);
        System.out.println(" Balance: " + (totalProducidas - totalConsumidas));
        System.out.println("==================================================\n");
    
    }
}

