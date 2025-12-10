public class Consumidor extends Thread{
    private Evento evento;
    private String nombre;

    public Consumidor(Evento e, String nombre) {
        evento = e;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i < 21; i++) {
            evento.consumirEvento();
        }
        System.out.println("\nEl laboratorio de Hawkins ya proceso 20 eventos\n");
    }
}
