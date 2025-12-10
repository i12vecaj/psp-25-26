package hawkins.modelo;

public class Evento {

    private final String descripcion;

    public Evento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Evento: " + descripcion;
    }
}

