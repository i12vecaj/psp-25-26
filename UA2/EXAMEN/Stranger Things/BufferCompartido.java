import java.util.LinkedList;
import java.util.Queue;

public class BufferCompartido {
    private final Queue<String> buffer = new LinkedList<>();
    private final int MAX_SIZE;

    public BufferCompartido(int size) {
        this.MAX_SIZE = size;
        System.out.println("Buffer compartido inicializado con tamaño máximo: " + MAX_SIZE);
    }

    public void put(String item) throws InterruptedException {
        synchronized (this) {
            while (buffer.size() == MAX_SIZE) {
                System.out.println("Buffer Lleno");
                wait();
            }

            buffer.add(item);
            System.out.println("Evento Generado: '" + item + "'. Tamaño actual: " + buffer.size());

            notifyAll();
        }
    }

    public String get() throws InterruptedException {
        synchronized (this) {
            while (buffer.isEmpty()) {
                System.out.println("Portales estables");
                wait();
            }

            String item = buffer.poll();
            System.out.println("Evento Procesado: '" + item + "'. Tamaño actual: " + buffer.size());

            notifyAll();
            return item;
        }
    }
}