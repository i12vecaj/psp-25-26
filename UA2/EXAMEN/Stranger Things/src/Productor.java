import java.util.ArrayList;

public class Productor {

    private String nombre;
    private ArrayList<String> eventos;

    public Productor(String nombre){
        this.nombre = nombre;
        eventos = new ArrayList<>(5);

    }

    public synchronized void ProducirEvento(){
        String[] events = {"Portal inestable","Portal abierto","Demogorgon detectado",
                "Demogorgon eliminado","Todo en orden"};
        while (eventos.size() == 5) {
            try {
                wait();
                System.out.println(this.nombre + "Esperas por buffer lleno/vacio: ");
            } catch (InterruptedException e) { }
        }


        String eventoElegido = events[(int) Math.floor(Math.random()* events.length)];
        eventos.add(eventoElegido);
        System.out.println(this.nombre + " Generando evento ");
        notifyAll();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<String> eventos) {
        this.eventos = eventos;
    }
}
