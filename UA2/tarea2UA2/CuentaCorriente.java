import java.util.Random;

public class CuentaCorriente {
    private double saldo;
    private final Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        if (saldoInicial < 0) {
            System.err.println("Error: Saldo inicial negativo. Ajustado a 0.0.");
            this.saldo = 0.0;
        } else {
            this.saldo = saldoInicial;
        }
    }

    public double getSaldo() {
        try {
            Thread.sleep(random.nextInt(1751) + 250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error en getter.");
        }
        return saldo;
    }

    public void setSaldo(double nuevoSaldo) {
        try {
            Thread.sleep(random.nextInt(1751) + 250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error en setter.");
        }
        this.saldo = nuevoSaldo;
    }

    public synchronized void realizarIngreso(double cantidad, String nombreHilo) {
        if (cantidad <= 0) {
            System.err.println("[" + nombreHilo + "] Ingreso fallido: cantidad invalida.");
            return;
        }

        double saldoPrevio = getSaldo();
        double saldoFinal = saldoPrevio + cantidad;

        System.out.printf("[%s] [SYNC] Pre: %.2f + Ingreso: %.2f -> Post: %.2f%n", nombreHilo, saldoPrevio, cantidad, saldoFinal);

        setSaldo(saldoFinal);
    }

    public void realizarIngresoNoSincronizado(double cantidad, String nombreHilo) {
        if (cantidad <= 0) {
            System.err.println("[" + nombreHilo + "] Ingreso fallido: cantidad invalida.");
            return;
        }

        double saldoPrevio = getSaldo();
        double saldoFinal = saldoPrevio + cantidad;

        System.out.printf("[%s] [NO-SYNC] Pre: %.2f + Ingreso: %.2f -> Post: %.2f%n", nombreHilo, saldoPrevio, cantidad, saldoFinal);

        setSaldo(saldoFinal);
    }
}