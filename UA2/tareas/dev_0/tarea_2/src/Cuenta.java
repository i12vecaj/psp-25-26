import java.util.Random;

public class Cuenta {
private double saldo;

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized void setSaldo(double saldo) {
        try {
            Thread.sleep(new Random().nextInt(1751) + 250);
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
        this.saldo = saldo;
    }

    public synchronized double getSaldo() {
        try {
            Thread.sleep(new Random().nextInt(1751) + 250);
        } catch (InterruptedException e) {
            System.out.println("Error en getSaldo: " + e.getMessage());
        }
        return saldo;
    }

    public synchronized void ingresar(String nombreHilo, double cantidad) {
        System.out.println("[" + nombreHilo + "] Estado: " + saldo);
        double nuevoSaldo = saldo + cantidad;
        setSaldo(nuevoSaldo);
        System.out.println("[" + nombreHilo + "] Final: " + nuevoSaldo);
    }
}