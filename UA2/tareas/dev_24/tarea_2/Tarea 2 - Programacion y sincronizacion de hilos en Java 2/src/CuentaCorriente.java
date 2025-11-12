import java.util.Random;

public class CuentaCorriente {
    private double saldo;
    private Random random;

    // Constructor
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
        this.random = new Random();
    }

    // Getter con sleep aleatorio
    public double getSaldo() {
        try {
            int milisegundos = 250 + random.nextInt(1751); // 250 a 2000 ms
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.err.println("Hilo interrumpido: " + e.getMessage());
        }
        return saldo;
    }

    // Setter con sleep aleatorio
    public void setSaldo(double saldo) {
        try {
            int milisegundos = 250 + random.nextInt(1751); // 250 a 2000 ms
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.err.println("Hilo interrumpido: " + e.getMessage());
        }
        this.saldo = saldo;
    }

    // Metodo synchronized para realizar ingresos
    public synchronized void ingreso(double cantidad, String nombreCliente) {
        double saldoPrevio = this.saldo;

        System.out.println("=== Ingreso realizado por: " + nombreCliente + " ===");
        System.out.println("Saldo previo: " + saldoPrevio + " €");

        // Simular operación con sleep
        try {
            int milisegundos = 250 + random.nextInt(1751);
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.err.println("Hilo interrumpido: " + e.getMessage());
        }

        this.saldo += cantidad;

        System.out.println("Cantidad ingresada: " + cantidad + " €");
        System.out.println("Saldo final: " + this.saldo + " €");
    }
}
