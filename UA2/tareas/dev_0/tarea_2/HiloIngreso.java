/**
 * Clase que extiende Thread. Su tarea es realizar un ingreso
 * en una CuentaCorriente compartida.
 */
public class HiloIngreso extends Thread {

    // Objeto CuentaCorriente compartido por todos los hilos
    private final CuentaCorriente cuenta;

    // Cantidad de dinero que este hilo va a ingresar
    private final double cantidadIngreso;

    // Nombre del hilo (para identificación en la salida)
    private final String nombre;

    /**
     * Constructor del hilo.
     * 
     * @param cuenta   Objeto CuentaCorriente compartido.
     * @param nombre   Nombre del hilo/ingresor.
     * @param cantidad Cantidad a ingresar.
     */
    public HiloIngreso(CuentaCorriente cuenta, String nombre, double cantidad) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.cantidadIngreso = cantidad;
    }

    /**
     * Método run() que ejecuta la tarea del hilo: llamar al método
     * de ingreso de la cuenta compartida.
     */
    @Override
    public void run() {
        // Llama al método ingresarCantidad, que está sincronizado en CuentaCorriente
        cuenta.ingresarCantidad(cantidadIngreso, nombre);
    }
}
