public class HilosLaboratorio implements Runnable{
    private String nombre;
    private ElevenProductor eleven;

    public HilosLaboratorio(String nombre, ElevenProductor eleven) {
        super();
        this.nombre = nombre;
        this.eleven = eleven;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ElevenProductor getEvento() {
        return eleven;
    }

    public void setEvento(ElevenProductor eleven) {
        this.eleven = eleven;
    }

    @Override
    public String toString() {
        return "HilosLaboratorio{" +
                "nombre='" + nombre + '\'' +
                ", evento=" + eleven +
                '}';
    }

    @Override
    public void run(){
        System.out.println("Hilo ejecutandose");
        for(int i = 0; i<20; i++){

            Evento eventoNuevo = eleven.abrirPortal();
            System.out.println("Eleven genera un evento.");

            System.out.println(nombre+" procesa un evento: "+eventoNuevo.getTipo());

            eleven.cerraPortal(eventoNuevo);
            System.out.println("Eleven cierra portal");

        }
    }
}
