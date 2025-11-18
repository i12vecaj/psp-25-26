package tarea2procesos;

public class ejec extends Thread{
	
	private CuentaCorriente cuenta;
    private double cantidad;

    public ejec(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        try {
            cuenta.incrementarsaldo(cantidad);
        } catch(Exception e) {
            System.err.println("Error en el hilo " + this.getName() + ": " + e.getMessage());
        }
    }
	
}
