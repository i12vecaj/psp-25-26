/*
Ejercicio2 Examen
Alejandro Prieto Mellado
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Laboratorio {
    public static final int TOTAL_EVENTOS = 20;
    public static final int TAMAÑO_BUFFER = 5;

    public static void main(String[] args) {
        System.out.println("[MAIN] Iniciando aplicación Stranger Things - Laboratorio de Hawkins");
        System.out.println("==================================================\n");
        
        BufferCompartido buffer = new BufferCompartido(TAMAÑO_BUFFER);
        
        Eleven eleven = new Eleven("Eleven", buffer, TOTAL_EVENTOS);
        
        LaboratorioHawkins laboratorio = new LaboratorioHawkins("Laboratorio Hawkins", buffer, TOTAL_EVENTOS);
        
        eleven.start();
        laboratorio.start();
        
        System.out.println("[MAIN] Eleven y el Laboratorio han iniciado sus tareas.\n");
        
        try {
            eleven.join();
            laboratorio.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n==================================================");
        System.out.println("[MAIN] Programa finalizado. Todos los eventos procesados.");
    }
}

class Evento {
    private final int id;
    private final String tipo;
    private static int contador = 1;
    private static final String[] TIPOS_EVENTOS = {
        "Demogorgon detectado",
        "Portal inestable",
        "Mind Flayer cercano",
        "Anomalía en el Upside Down",
        "Energía dimensional elevada"
    };
    private static final Random rnd = new Random();

    public Evento() {
        this.id = contador++;
        this.tipo = TIPOS_EVENTOS[rnd.nextInt(TIPOS_EVENTOS.length)];
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Evento #" + id + " [" + tipo + "]";
    }
}

class BufferCompartido {
    private final String nombre;
    private final int tamañoMaximo;
    private final List<Evento> eventos;

    public BufferCompartido(int capacidad) {
        this.nombre = "Buffer de Eventos";
        this.tamañoMaximo = capacidad;
        this.eventos = new ArrayList<>();
        System.out.println("[BufferCompartido] Creado con capacidad: " + capacidad);
    }

    public synchronized void agregarEvento(Evento evento, String nombreProductor) {
        while (eventos.size() >= tamañoMaximo) {
            try {
                System.out.println("⏳ [" + nombreProductor + "] Buffer lleno. Esperando espacio...");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        eventos.add(evento);
        System.out.println("✨ [" + nombreProductor + "] Generó: " + evento);
        mostrarEstado();
        
        notifyAll();
    }

    public synchronized Evento consumirEvento(String nombreConsumidor) {
        // Si el buffer está vacío, el consumidor debe esperar
        while (eventos.isEmpty()) {
            try {
                System.out.println("⏳ [" + nombreConsumidor + "] Buffer vacío. Esperando eventos...");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
                return null;
            }
        }

        Evento evento = eventos.remove(0);
        System.out.println("🔬 [" + nombreConsumidor + "] Procesando: " + evento);
        mostrarEstado();
        
        notifyAll();
        return evento;
    }

    private void mostrarEstado() {
        System.out.println("📊 [Buffer] Eventos en buffer: " + eventos.size() + "/" + tamañoMaximo);
    }
}

class Eleven extends Thread {
    private final BufferCompartido buffer;
    private final int totalEventos;
    private final Random rnd = new Random();

    public Eleven(String nombre, BufferCompartido buffer, int totalEventos) {
        super(nombre);
        this.buffer = buffer;
        this.totalEventos = totalEventos;
        System.out.println("[Eleven] Eleven está lista para cerrar portales.");
    }

    @Override
    public void run() {
        System.out.println("[Eleven] Iniciando producción de eventos.");
        try {
            for (int i = 0; i < totalEventos; i++) {

                Evento evento = new Evento();
                
                buffer.agregarEvento(evento, getName());
  
                int espera = 100 + rnd.nextInt(400);
                Thread.sleep(espera);
            }
            System.out.println("\n✅ [Eleven] Todos los portales cerrados. Total: " + totalEventos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[Eleven] Interrumpida durante la producción.");
        }
    }
}

class LaboratorioHawkins extends Thread {
    private final BufferCompartido buffer;
    private final int totalEventos;
    private final Random rnd = new Random();
    private int eventosProcessados = 0;

    public LaboratorioHawkins(String nombre, BufferCompartido buffer, int totalEventos) {
        super(nombre);
        this.buffer = buffer;
        this.totalEventos = totalEventos;
        System.out.println("[LaboratorioHawkins] Laboratorio preparado para analizar eventos.");
    }

    @Override
    public void run() {
        System.out.println("[LaboratorioHawkins] Iniciando análisis de eventos.");
        try {
            while (eventosProcessados < totalEventos) {

                Evento evento = buffer.consumirEvento(getName());
                
                if (evento != null) {
                    int procesamientoMs = 200 + rnd.nextInt(500);
                    Thread.sleep(procesamientoMs);
                    
                    eventosProcessados++;
                    System.out.println("✅ [LaboratorioHawkins] Evento procesado en " + procesamientoMs + "ms. Total: " + eventosProcessados + "/" + totalEventos);
                }
            }
            System.out.println("\n🎯 [LaboratorioHawkins] Todos los eventos analizados. Total: " + eventosProcessados);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[LaboratorioHawkins] Interrumpido durante el análisis.");
        }
    }
}
