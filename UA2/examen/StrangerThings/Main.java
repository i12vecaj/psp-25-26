import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ElevenProductor eleven = new ElevenProductor(new ArrayList<>(), "Eleven");

        Thread hilo1 = new Thread(new HilosLaboratorio("Laboratorio", eleven));
        Thread hilo2 = new Thread(new HilosLaboratorio("Laboratorio", eleven));
        Thread hilo3 = new Thread(new HilosLaboratorio("Laboratorio", eleven));

        hilo1.start();
        hilo2.start();
        hilo3.start();

    }
}