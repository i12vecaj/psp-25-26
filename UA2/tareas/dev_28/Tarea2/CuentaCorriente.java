import java.util.Random;

public class CuentaCorriente  {
    private double saldo;
    private Random random = new Random();

    public CuentaCorriente(int saldoInicial) {
        saldo = saldoInicial;
    }
// Dentro del get y del set hacemos un sleep al hilo
    public double getSaldo() {
        try {
            int tiempo = 250 + random.nextInt(1751);
            Account.sleep(tiempo);
            return saldo;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSaldo(int cantidad) {
        try {
            int tiempo = 250 + random.nextInt(1751);
            Account.sleep(tiempo);
            this.saldo = cantidad;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /* Funcion sincronizada para ingresar dinero. Si quitamos la sincronizacion el resultado obtenido
       no sera correcto. Explicacion mas detallada en el main
     */

    public synchronized void ingreso(double cantidad, String persona) {
        System.out.println("Saldo actual: " + this.saldo);
        this.saldo+= cantidad;
        System.out.println("Dinero ingresado por " + persona + " : " + cantidad);
        System.out.println("Saldo actual despues del ingreso " + this.saldo);
    }
}
