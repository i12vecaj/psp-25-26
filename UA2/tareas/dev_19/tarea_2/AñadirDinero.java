package Tarea2;

public class AñadirDinero extends Thread{
    private final CuentaCorriente cuenta;
    private final double cantidad;

    public AñadirDinero(CuentaCorriente cuenta,  String nombre,double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run(){
        try{
            cuenta.ingresarDinero(getName(), cantidad);
        }catch (Exception e){
            System.out.println("Error en el hilo: "+getName()+", "+e.getMessage());
        }
    }

}
