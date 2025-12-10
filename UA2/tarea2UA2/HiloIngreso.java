public class HiloIngreso extends Thread {
    private final CuentaCorriente cuenta;
    private final double cantidad;
    private final boolean sincronizado;

    public HiloIngreso(CuentaCorriente cuenta, double cantidad, String nombre, boolean sincronizado) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.sincronizado = sincronizado;
    }

    @Override
    public void run() {
        if (sincronizado) {
            cuenta.realizarIngreso(cantidad, getName());
        } else {
            cuenta.realizarIngresoNoSincronizado(cantidad, getName());
        }
    }
}