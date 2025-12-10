package hawkins.consumidor;

import hawkins.buffer.EventBuffer;
import hawkins.modelo.Evento;

public class LaboratorioConsumidor implements Runnable {

    private final EventBuffer buffer;
    private final int totalEventos;
    private int procesados = 0;

    public LaboratorioConsumidor(EventBuffer buffer, int totalEventos) {
        this.buffer = buffer;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run() {
        try {
            while (procesados < totalEventos) {

                Evento evento = buffer.consumir();
                procesados++;

                Thread.sleep(600); // simula el análisis del laboratorio
            }

        } catch (InterruptedException e) {
            System.out.println("Laboratorio interrumpido mientras procesaba.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Laboratorio ha terminado de procesar eventos.");
    }
}
