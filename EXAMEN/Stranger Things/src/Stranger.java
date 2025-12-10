import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stranger {
    public static void main(String[] args) {

        TipoEvento tipoEvento = new TipoEvento();
        int totalEventos = 20;

        Productor eleven = new Productor("Eleven ", tipoEvento, totalEventos);
        Consumidor laboratorio = new Consumidor("Laboratorio ", tipoEvento, totalEventos);

        eleven.start();
        laboratorio.start();

        try {
            eleven.join();
            laboratorio.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Hawkins está a salvo. Programa finalizado.");
    }
}

// =============
// Clase TipoEventoo
// ==============
class TipoEvento {
    //Array Eventos
    private List<String> tipo = new ArrayList<>();
    private final int capacidad = 5;

    synchronized void agregar(String evento) throws InterruptedException {

        //Limitamos la capacidad a 5
        while (tipo.size() == capacidad) {
            System.out.println("TipoEvento lleno. Productor espera...");
            //Espera
            wait();
        }
        tipo.add(evento);
        System.out.println(" Eleven genera un evento: " + evento + " TipoEvento: " + tipo.size() + " " + capacidad);
        // Notifica a todos los hilos que de nuevo arranquen
        notifyAll();
    }

    synchronized String obtener() throws InterruptedException {
        // Si esta vacio envia un mensaje y para el hilo
        while (tipo.isEmpty()) {
            System.out.println("Si el tipoEvento esta vacio, el consumidor espera...");
            //espera
            wait();
        }
        String evento = tipo.remove(0);
        System.out.println("Laboratorio: " + evento + " TipoEvento: " + tipo.size() + " " + capacidad);
        return evento;
    }
}

// ======================
// Clase Productor (Eleven)
// ======================
class Productor extends Thread {
    private TipoEvento tipoEvento;
    private int eventos;
    private String nombre;

    public Productor(String nombre, TipoEvento tipoEvento, int eventos) {
        this.nombre = nombre;
        this.tipoEvento = tipoEvento;
        this.eventos = eventos;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < eventos; i++) {
            try {
                String evento;
                evento= "Evento: " + (i + 1) + " Anomalía tipo: " + random.nextInt(1000);
                tipoEvento.agregar(evento);
                Thread.sleep(random.nextInt(500) + 200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Productor " + nombre + " ha cerrado todos los portales.");
    }
}

// ================
// Clase Consumidor
// ================
class Consumidor extends Thread {
    private TipoEvento tipoEvento;
    private int eventos;
    private String nombre;

    public Consumidor(String nombre, TipoEvento tipoEvento, int eventos) {
        this.nombre = nombre;
        this.tipoEvento = tipoEvento;
        this.eventos = eventos;
    }

    @Override
    public void run() {
        for (int i = 0; i < eventos; i++) {
            try {
                tipoEvento.obtener();
                Thread.sleep(new Random().nextInt(400) + 300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Consumidor " + nombre + " ha procesado todos los eventos.");
    }
}