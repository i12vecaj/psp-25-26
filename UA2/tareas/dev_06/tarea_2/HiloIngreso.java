
public class HiloIngreso extends Thread {
    private final CuentaCorriente cuenta;
    private final double cantidad;

    public HiloIngreso(CuentaCorriente cuenta, double cantidad, String nombre) {
        super(nombre); // Nombre del hilo
        this.cuenta = cuenta; // Objeto CuentaCorriente compartido
        this.cantidad = cantidad; // Cantidad a ingresar
    }

    @Override
    public void run() { // Ejecuta el ingreso en la cuenta
        try {
            cuenta.ingresar(cantidad, getName());
        } catch (Exception e) {
            System.err.println("Error en " + getName() + ": " + e.getMessage());
        }
    }
}