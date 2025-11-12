/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Unidad: UA2 - Tarea 2 HiloIngreso
 *
 * Extiende Thread y representa un hilo que realiza un ingreso
 * en una cuenta corriente compartida.
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
        cuenta.ingresar(getName(), cantidad);
    }
}
