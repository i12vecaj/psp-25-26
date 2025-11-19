public class Ingreso extends Thread {
    private Cuenta cuenta;
    private double cantidad;

    public Ingreso(String nombre, Cuenta cuenta, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresar(getName(), cantidad);
    }
}
