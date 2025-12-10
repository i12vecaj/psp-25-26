/*
Nombre: Jose Antonio Roda Donoso
Fecha: 10/12/2025
Curso: 2DAM
Este hilo genera eventos y los mete en el buffer.
 */

public class Productor extends Thread {
    private final Buffer buffer;
    private final int totalEventos;

    public Productor(Buffer buffer, int totalEventos) {
        this.buffer = buffer;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run() {
        // Eventos que puede generar
        String[] posiblesEventos = {
                "Demogorgon detectado",
                "Portal abierto",
                "Rasgo electromagnético",
                "Una criatura",
                "Actividad psíquica"
        };

        try {
            for (int i = 0; i < totalEventos; i++) {
                String evento = posiblesEventos[i % posiblesEventos.length];

                buffer.producir(evento);

                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            System.out.println("Productor interrumpido");
        }
    }
}
