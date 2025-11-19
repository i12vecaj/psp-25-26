import java.util.Random;

public class CuentaCorriente {

    private double saldo;

    private Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Función de mimir aleatoria entre 250 y 2000 ms
    // para limpiar el código
    private void dormirAleatorio() {
        int tiempo = (int) (Math.random() * (2000 - 250 + 1)) + 250; // Hay que castear
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Getter del saldo
    public double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    // Setter del saldo
    public void setSaldo(double nuevoSaldo) {
        dormirAleatorio();
        this.saldo = nuevoSaldo;
    }

    // Hay que sincronizar este método para que no se líe
    public synchronized void ingresarDinero(String nombreHilo, double cantidad) {
        if (cantidad > 0) {
            double saldoPrevio = this.saldo;
            dormirAleatorio();
            this.saldo += cantidad;
            System.out.println(nombreHilo +
                    "\n - Saldo: " + saldoPrevio +
                    "\n - Ingreso: " + cantidad +
                    "\n - Saldo resultante: " + this.saldo);
            System.out.println("=================================");
        }
    }
}