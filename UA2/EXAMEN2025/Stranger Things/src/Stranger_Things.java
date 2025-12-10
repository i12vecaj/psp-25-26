/*
Autor: Antonio Rodriguez Cortés
Fecha: 10/12/2025
Clase: 2º DAM
*/

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Stranger_Things {
    private static final int BUFFER_SIZE = 7;
    private static final int TOTAL_EVENTS = 23;

    public static void main(String[] args) throws InterruptedException {
        EventBuffer buffer = new EventBuffer(BUFFER_SIZE);


    //CREAMOS LOS DOS HILOS QUE NECESITO "ELEVEN" Y "LABORATORIO"
        Thread producer = new Thread(new Producer(buffer, TOTAL_EVENTS), "Eleven");
        Thread consumer = new Thread(new Consumer(buffer, TOTAL_EVENTS), "Laboratorio");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\n|===========================================|");
        System.out.println("|Trabajo completado ningun evento pendiente.|");
        System.out.println("|===========================================|");
    }

    //CLASE BUFFER PARA ALMACENAR LOS EVENTOS
    static class EventBuffer {
        private final Queue<String> queue = new LinkedList<>();
        private final int capacity;

        EventBuffer(int capacity) {
            this.capacity = capacity;
        }

    //METODO PARA AÑADIR EVENTOS AL BUFFER
        public synchronized void put(String event) throws InterruptedException {
            while (queue.size() == capacity) {
                System.out.println("Buffer lleno...");
                wait();
            }

            queue.add(event);
            System.out.println("Evento depositado: " + event + " (tam: " + queue.size() + ")");
            notifyAll();
        }

    //METODO PARA SACAR EVENTOS DEL BUFFER
        public synchronized String take() throws InterruptedException {
            while (queue.isEmpty()) {
                System.out.println("Buffer vacio...");
                wait();
            }

            String event = queue.poll();
            System.out.println("Evento recogido: " + event + " (tam: " + queue.size() + ")");
            notifyAll();
            return event;
        }
    }

    //HILO QUE GENERA LOS EVENTOS Y LOS AÑADE AL BUFFER
    static class Producer implements Runnable {
        private final EventBuffer buffer;
        private final int total;
        private final Random random = new Random();
        private final String[] templates = {
                "Demogorgon detectado",
                "Portal apagandose",
                "Luces luminosas",
                "Ruido en pasillo",
                "Sustos que dan miedo" };

        Producer(EventBuffer buffer, int total) {
            this.buffer = buffer;
            this.total = total;
        }

    //METODO RUN DONDE SE GENERAN LOS EVENTOS
        @Override
        public void run() {
            for (int i = 1; i <= total; i++) {
                String event = templates[random.nextInt(templates.length)];
                System.out.println("Eleven genera evento: " + event);
                try {
                    buffer.put(event);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    //HILO QUE BORRA LOS EVENTOS DEL BUFFER
    static class Consumer implements Runnable {
        private final EventBuffer buffer;
        private final int total;
        private final Random random = new Random();

        Consumer(EventBuffer buffer, int total) {
            this.buffer = buffer;
            this.total = total;
        }

    //METODO RUN DONDE SE TERMINA LOS EVENTOS
        @Override
        public void run() {
            for (int i = 0; i < total; i++) {
                try {
                    String event = buffer.take();
                    System.out.println("Laboratorio procesando evento: " + event);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}

