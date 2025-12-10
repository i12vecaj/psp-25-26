/*
Nombre: Jose Antonio Roda Donoso
Fecha: 10/12/2025
Curso: 2DAM
Este hilo recoge los eventos que genera y los procesa.
 */

public class Consumidor extends Thread {
    private final Buffer buffer;
    private final int totalEventos;

    public Consumidor(Buffer buffer, int totalEventos) {
        this.buffer = buffer;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalEventos; i++) {

                String evento = buffer.consumir();
                System.out.println("Laboratorio procesa: " + evento);

                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumidor interrumpido");
        }
    }
}
