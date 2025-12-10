public class VasoCerveza {
    int id;
    int tipo;

    public VasoCerveza(int tipo, int id) {
        this.tipo = tipo;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "VasoCerveza{" +
                "id=" + id +
                ", tipo=" + tipo +
                '}';
    }
}