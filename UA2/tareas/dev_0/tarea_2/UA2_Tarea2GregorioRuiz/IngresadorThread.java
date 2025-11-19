/*
  IngresadorThread.java: hilo que realiza un ingreso en una CuentaCorriente compartida.
  Extiende Thread y en run() llama a la versión sincronizada o no sincronizada según se indique.
*/

public class IngresadorThread extends Thread {
    private final CuentaCorriente cuenta;
    private final int cantidad;
    private final boolean usarSync;

    public IngresadorThread(CuentaCorriente cuenta, String nombre, int cantidad, boolean usarSync) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.usarSync = usarSync;
    }

    @Override
    public void run() {
        try {
            if (usarSync) cuenta.ingresarSync(getName(), cantidad);
            else cuenta.ingresarNoSync(getName(), cantidad);
        } catch (IllegalArgumentException e) {
            System.err.printf("Error en hilo %s: %s%n", getName(), e.getMessage());
        } catch (Exception e) {
            System.err.printf("Excepción inesperada en hilo %s: %s%n", getName(), e.toString());
        }
    }
}