public class HiloIngreso extends Thread{


    private CuentaCorriente cuenta;
    private double cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {

        cuenta.ingresar(this.getName(), cantidad); //Uso this para acceder a este hilo una vez esté creado, entonces como
                                                    // la clase Thread qeu es de la que extiende requiere de un nombre para la creación del hilo uso super para acceder al nombre de la clase que hereda

    }
}
