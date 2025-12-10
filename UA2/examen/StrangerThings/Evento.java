public class Evento {
    private int id;
    private String tipo;


    public Evento(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized String getTipo() {
        return tipo;
    }

    public synchronized void setTipo(String tipo) {
        this.tipo = tipo;
    }


    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
