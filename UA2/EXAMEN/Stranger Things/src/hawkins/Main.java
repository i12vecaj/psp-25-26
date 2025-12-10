package hawkins;

import hawkins.buffer.EventBuffer;
import hawkins.consumidor.LaboratorioConsumidor;
import hawkins.productor.ElevenProductor;

public class Main {
    public static void main(String[] args) {

        final int capacidadBuffer = 5;
        final int eventosTotales = 20;

        EventBuffer buffer = new EventBuffer(capacidadBuffer);

        Thread productor = new Thread(new ElevenProductor(buffer, eventosTotales));
        Thread consumidor = new Thread(new LaboratorioConsumidor(buffer, eventosTotales));

        productor.start();
        consumidor.start();

        try {
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("FIN DEL PROGRAMA");
    }
}
