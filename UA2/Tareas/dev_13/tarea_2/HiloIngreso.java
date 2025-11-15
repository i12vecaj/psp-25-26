public class HiloIngreso extends Thread {

    private CuentaCorriente cuenta;
    private double cantidad;

    // Constructor: recibe la cuenta, la cantidad y el nombre del hilo
    public HiloIngreso(CuentaCorriente cuenta, double cantidad, String nombre) {
        super(nombre); // asigna el nombre del hilo
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresar(getName(), cantidad);
    }
}
