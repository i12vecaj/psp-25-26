
public class Productor extends Thread{
private Evento evento;
private String nombre;

    public Productor(Evento e, String nombre) {
        evento = e;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i < 21; i++) {
        evento.crearEvento(i);
        }
        System.out.println("\nEleven ya genero 20 eventos\n");
    }
}
