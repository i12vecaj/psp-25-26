public class Funcion extends Thread {
    //Atributos para almacenar la cuenta compartida y los detalles del ingreso
    private CuentaCorriente cuenta;
    private String nombreUsuario;
    private double cantidadIngresar;

    // Constructor para recibir el objeto CuentaCorriente compartido y los detalles del ingreso
    public Funcion(CuentaCorriente cuenta, String nombreUsuario, double cantidadIngresar) {
        this.cuenta = cuenta;
        this.nombreUsuario = nombreUsuario;
        this.cantidadIngresar = cantidadIngresar;
        // Asignamos el nombre al hilo para la traza de logs
        setName(nombreUsuario);
    }

    @Override
    public void run() {
        // FR2: Desde run(), se llama al metodo de la clase CuentaCorriente que añade saldo
        try {
            cuenta.ingresar(nombreUsuario, cantidadIngresar);
        } catch (Exception e) {
            // Control de errores básico
            System.err.println("Excepción no controlada en el hilo " + nombreUsuario + ": " + e.getMessage());
        }
    }
}