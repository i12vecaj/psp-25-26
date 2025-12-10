import java.util.LinkedList;

public class Buffer {
    private final LinkedList<String> buffer;
    private final int maxSize;

    public Buffer(int size) {
        this.buffer = new LinkedList<>();
        this.maxSize = size;
    }

    public synchronized void producir(String evento) throws InterruptedException {
        while (buffer.size() == maxSize) {
            System.out.println("Buffer lleno. Eleven espera para producir.");
            wait();
        }
        buffer.add(evento);
        System.out.println("Eleven genera un evento: " + evento);
        notifyAll();
    }

    public synchronized String consumir() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("Buffer vacío. Laboratorio espera para consumir.");
            wait();
        }

        String evento = buffer.poll(); // Extrae y elimina el evento del buffer, pero esta linea no sabia hacerla y me ayudó la IA integrada, me estaba dando error y era por esta linea, que se que no la hemos dado
        System.out.println("Laboratorio procesa un evento: " + evento);
        notifyAll();
        return evento;
    }
}