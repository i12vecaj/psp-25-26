//Clase cerveza, le meto una función to string para saber que vaso es
class VasoCerveza {
    private int id;
    private int tipo;

    public VasoCerveza(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "Vaso{id=" + id + ", tipo=" + (tipo == 0 ? "Media Pinta" : "Pinta") + "}";
    }
}