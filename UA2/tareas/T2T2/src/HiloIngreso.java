class HiloIngreso extends Thread {
    private CuentaCorriente cuenta;
    private double cantidad;
    
    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre);
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser null");
        }
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }
    
    @Override
    public void run() {
        try {
            cuenta.ingresarDinero(cantidad, getName());
        } catch (Exception e) {
            System.err.println("Error en " + getName() + ": " + e.getMessage());
        }
    }
}