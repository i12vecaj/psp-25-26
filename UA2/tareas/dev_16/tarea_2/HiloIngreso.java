class HiloIngreso extends Thread {
    private CuentaCorriente cuenta;
    private double cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresarDinero(getName(), cantidad);
    }
}