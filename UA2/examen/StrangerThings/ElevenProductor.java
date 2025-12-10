import java.util.List;
import java.util.Random;

public class ElevenProductor {
    private List<Evento> listaEventos;
    private String nombre;

    public ElevenProductor(List<Evento> listaEventos, String nombre) {
        this.listaEventos = listaEventos;
        this.nombre = nombre;

        listaEventos.add(new Evento(1,"Demogorgon detectado"));
        listaEventos.add(new Evento(2,"Portal inestable"));
        listaEventos.add(new Evento(3,"Mike vio a once"));

    }

    public synchronized void cerraPortal(Evento evento){
        if(evento == null){ //Si el evento que recibe es nulo no lo acepta
            System.out.println("-"+nombre+" recibe un evento nulo, no lo acepta");
            return;
        }
        listaEventos.add(evento); //Se añade evento a la lista de vuelta
        System.out.println("Se añade evento a la lista");
        notifyAll(); //Notifica que se ha devuelto evento
    }

    public synchronized Evento abrirPortal(){
        while (listaEventos.isEmpty()){
            try{
                System.out.println("No hay portales por abrir, se espera");
                wait();
            } catch (InterruptedException e) {
                System.out.println("Error a la hora de abrir portales: "+e.getMessage());
            }
        }
        Random random = new Random();
        int indiceLista = random.nextInt(listaEventos.size());
        Evento evento = listaEventos.remove(indiceLista);
        System.out.println("-" + nombre + " abre portal " + evento);
        return evento;
    }

}
