
//Creamos una clase que extienda Thread
public class Account extends Thread{

    private CuentaCorriente cuenta;
    private double cantidad;
    private String nombre;

    //Constructor de la clase account
    public Account(CuentaCorriente cuenta,double cantidad,String nombre) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    @Override
    // Realizamos el ingreso
    public void run() {
        cuenta.ingreso(this.cantidad,this.nombre);
    }
}
