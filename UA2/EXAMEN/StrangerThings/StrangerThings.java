import java.util.LinkedList;
import java.util. Queue;
import java.util. Random;

public class StrangerThings {
    
    static class Buffer {
        private Queue<String> eventos;
        private int capacidad;
        
        public Buffer(int capacidad) {
            this.eventos = new LinkedList<>();
            this.capacidad = capacidad;
        }
        
        public synchronized void producir(String evento) throws InterruptedException {
            while (eventos.size() == capacidad) {
                System.out.println("[ELEVEN] Buffer lleno. Esperando.. .");
                wait();
            }
            
            eventos.add(evento);
            System.out. println("[ELEVEN] Evento generado: " + evento);
            notifyAll();
        }
        
        public synchronized String consumir() throws InterruptedException {
            while (eventos.isEmpty()) {
                System.out.println("[LABORATORIO] Buffer vacio. Esperando.. .");
                wait();
            }
            
            String evento = eventos.poll();
            System.out.println("[LABORATORIO] Procesando: " + evento);
            notifyAll();
            
            return evento;
        }
    }
    
    static class Productor extends Thread {
        private Buffer buffer;
        private int totalEventos;
        private String[] tiposEventos = {
            "Demogorgon detectado",
            "Portal inestable",
            "Mindflayer avistado",
            "Demodogs en el area"
        };
        private Random random;
        
        public Productor(Buffer buffer, int totalEventos) {
            this.buffer = buffer;
            this.totalEventos = totalEventos;
            this.random = new Random();
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < totalEventos; i++) {
                    String evento = tiposEventos[random.nextInt(tiposEventos.length)] + " #" + (i + 1);
                    buffer.producir(evento);
                    Thread. sleep(random.nextInt(500) + 200);
                }
                System.out.println("[ELEVEN] Portales cerrados. Terminado.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class Consumidor extends Thread {
        private Buffer buffer;
        private int totalEventos;
        private Random random;
        
        public Consumidor(Buffer buffer, int totalEventos) {
            this.buffer = buffer;
            this.totalEventos = totalEventos;
            this.random = new Random();
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < totalEventos; i++) {
                    buffer.consumir();
                    Thread.sleep(random.nextInt(700) + 300);
                }
                System.out.println("[LABORATORIO] Analisis completado. Terminado.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("STRANGER THINGS - Productor/Consumidor");
        System.out.println("---------------------------------------");
        
        int capacidadBuffer = 5;
        int totalEventos = 20;
        
        Buffer buffer = new Buffer(capacidadBuffer);
        Productor eleven = new Productor(buffer, totalEventos);
        Consumidor laboratorio = new Consumidor(buffer, totalEventos);
        
        eleven.start();
        laboratorio. start();
        
        try {
            eleven.join();
            laboratorio.join();
        } catch (InterruptedException e) {
            System.out.println("Error en sincronizacion");
        }
        
        System.out.println("---------------------------------------");
        System.out. println("Simulacion completada");
    }
}