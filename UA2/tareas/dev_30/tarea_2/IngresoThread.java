/**
 * FR2: Hilo que realiza un ingreso en una CuentaCorriente desde run().
 */
public class IngresoThread extends Thread {
    private final CuentaCorriente cuenta;
    private final double cantidad;
    private final boolean sincronizado;

    /**
     * @param nombre nombre del hilo
     * @param cuenta cuenta compartida
     * @param cantidad cantidad a ingresar (> 0)
     * @param sincronizado true para usar ingresarSync, false para ingresarNoSync
     */
    public IngresoThread(String nombre, CuentaCorriente cuenta, double cantidad, boolean sincronizado) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.sincronizado = sincronizado;
    }

    @Override
    public void run() {
        try {
            if (sincronizado) {
                cuenta.ingresarSync(cantidad, getName());
            } else {
                cuenta.ingresarNoSync(cantidad, getName());
            }
        } catch (Exception e) {
            System.err.printf("%s: error en run(): %s%n", getName(), e.getMessage());
        }
    }
}