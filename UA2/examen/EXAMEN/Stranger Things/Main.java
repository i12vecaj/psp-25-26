/*
Nombre: Jose Antonio Roda Donoso
Fecha: 10/12/2025
Curso: 2DAM
Crea el buffer, crea al productor y al consumidor, y arranca los dos hilos
 */

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        int totalEventos = 20;

        Productor p = new Productor(buffer, totalEventos);
        Consumidor c = new Consumidor(buffer, totalEventos);

        p.start();
        c.start();
    }
}
