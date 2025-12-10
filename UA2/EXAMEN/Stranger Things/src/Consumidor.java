public class Consumidor extends Thread {
    private String nombre;
    private Productor productor;

    public Consumidor(String nombre, Productor productor){
        this.nombre = nombre;
        this.productor = productor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            if(productor.getEventos().isEmpty()){
                productor.ProducirEvento();
            }
            System.out.println(this.nombre + " " + productor.getEventos().getFirst());
            productor.getEventos().remove(productor.getEventos().removeFirst());
            if (productor.getEventos().isEmpty()){
                System.out.println(this.nombre + " Esperas por buffer lleno/vacio");
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
