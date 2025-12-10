/*
Nombre: Jose Antonio Roda Donoso
Fecha: 10/12/2025
Curso: 2DAM
Es el espacio compartido entre el productor y el consumidor
 */

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final Queue<String> cola = new LinkedList<>();
    private final int limite;

    public Buffer(int limite) {
        this.limite = limite;
    }

    // El productor añade un evento al buffer
    public synchronized void producir(String evento) throws InterruptedException {
        while (cola.size() == limite) {
            System.out.println("Buffer lleno. Eleven espera...");
            wait();
        }

        cola.add(evento);
        System.out.println("Eleven genera evento: " + evento);
        notifyAll();
    }

    // El consumidor recoge un evento del buffer
    public synchronized String consumir() throws InterruptedException {
        while (cola.isEmpty()) {
            System.out.println("Buffer vacío. Laboratorio espera...");
            wait();
        }

        String evento = cola.poll();
        notifyAll();
        return evento;
    }
}
