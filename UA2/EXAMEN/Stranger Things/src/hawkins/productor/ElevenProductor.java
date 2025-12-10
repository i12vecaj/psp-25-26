package hawkins.productor;

import hawkins.buffer.EventBuffer;
import hawkins.modelo.Evento;

public class ElevenProductor implements Runnable {

    private final EventBuffer buffer;
    private final int totalEventos;
    private int generados = 0;

    private final String[] posiblesEventos = {
            "Demogorgon detectado",
            "Portal inestable",
            "Ruidos desde el Upside Down",
            "Ondas psíquicas detectadas",
            "Suena: Should I stay or should I go",
            "Suena: Never ending story",
            "Ruidos de pequeños vampiros",
            "Ruidos de criaturas irreconocibles"
    };

    public ElevenProductor(EventBuffer buffer, int totalEventos) {
        this.buffer = buffer;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run() {
        try {
            while (generados < totalEventos) {

                String descripcion = posiblesEventos[(int) (Math.random() * posiblesEventos.length)];
                Evento nuevo = new Evento(descripcion);

                buffer.producir(nuevo);
                generados++;

                Thread.sleep(300); // para que se vea bien
            }

        } catch (InterruptedException e) {
            System.out.println("Eleven interrumpida mientras producía.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Eleven ha terminado de generar eventos.");
    }
}
