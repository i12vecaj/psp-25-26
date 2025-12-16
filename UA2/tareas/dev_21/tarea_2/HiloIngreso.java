package tarea_2;
/**
 * Clase que extiende de Thread y realiza un ingreso en la cuenta compartida.
 */
public class HiloIngreso extends Thread {
    private CuentaCorriente cuenta;
    private double cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresarDinero(cantidad, getName());
    }
}
