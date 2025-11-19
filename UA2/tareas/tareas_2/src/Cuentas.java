public class Cuentas extends Thread{

    private final CuentaCorriente cuenta;
    private final double cantidad;

    public Cuentas (String nombreHilo, CuentaCorriente nombre,  double cantidad){
        super(nombreHilo);
        this.cuenta = nombre;
        this.cantidad = cantidad;

    }

    public void run(){
        try {
            cuenta.ingresar(cantidad);
        }catch(Exception e){
            System.out.println("Error al ingresar");
        }
    }
}
