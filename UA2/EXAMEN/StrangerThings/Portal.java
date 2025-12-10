package StrangerThings;

class Portal {
    private String nombre;

    public Portal(String nombre) {
        this.nombre = nombre;
        System.out.println("Creado " + this);
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Portal: " + nombre;
    }
}
