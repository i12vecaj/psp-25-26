public class Main {

    static void main(String[] args) {
        Evento evento = new Evento();

        Productor p = new Productor(evento, "Eleven");
        Consumidor c = new Consumidor(evento, "Laboratorio");

        p.start();
        c.start();
    }

}
