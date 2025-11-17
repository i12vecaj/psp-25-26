class Hilo extends Thread {
    private cuentabanco cuenta;
    private double cantidad;

    public Hilo(String nombre, cuentabanco cuenta, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresarDinero(getName(), cantidad);
    }

}