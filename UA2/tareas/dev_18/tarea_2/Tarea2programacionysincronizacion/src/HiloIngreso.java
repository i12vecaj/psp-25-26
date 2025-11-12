/* T2 - Tarea 2 - Programación y sincronización de hilos en Java 2
 *FR2
 * Nombre: Alberto Nieto Lozano
 **/

public class HiloIngreso extends Thread {
    private final CuentaCorriente cuenta;
    private final double cantidad;
    private final String nombre;

    public HiloIngreso(CuentaCorriente cuenta, double cantidad, String nombre) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        cuenta.ingresar(cantidad, nombre);
    }
}
