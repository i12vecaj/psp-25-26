public class HiloIngreso extends Thread{
    private CuentaCorriente cuenta;
    private double cantidad;
    private String nombreCliente;

    // Constructor
    public HiloIngreso(CuentaCorriente cuenta, double cantidad, String nombreCliente) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.nombreCliente = nombreCliente;
    }

    // Metodo run que ejecuta el ingreso
    @Override
    public void run() {
        cuenta.ingreso(cantidad, nombreCliente);
    }
}
