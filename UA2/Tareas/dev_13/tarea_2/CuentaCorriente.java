import java.util.Random;

public class CuentaCorriente {
    // Atributo: saldo de la cuenta
    private double saldo;
    // Generador de números aleatorios para el retardo
    private Random random = new Random();

    // Constructor: asigna un saldo inicial
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Getter del saldo (añadir sleep aleatorio)
    public double getSaldo() {
        sleepAleatorio(); // sleep entre 250 y 2000 ms
        return saldo;
    }

    // Setter del saldo (añadir sleep aleatorio)
    public void setSaldo(double nuevoSaldo) {
        sleepAleatorio(); // sleep entre 250 y 2000 ms
        this.saldo = nuevoSaldo;
    }

    // Método helper que duerme entre 250 y 2000 ms
    private void sleepAleatorio() {
        int ms = 250 + random.nextInt(2000 - 250 + 1); // 250..2000
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Método synchronized para ingresar una cantidad
    public synchronized void ingresar(String nombreHilo, double cantidad) {
        if (cantidad < 0) {
            System.err.printf("%s: cantidad negativa no permitida: %.2f%n", nombreHilo, cantidad);
            return;
        }
        // 1. Mostrar por pantalla quién realiza el ingreso y el saldo previo.
        System.out.printf("%s inicia ingreso. Saldo previo: %.2f%n", nombreHilo, saldo);

        // 2. Sumar la cantidad al saldo.
        double nuevoSaldo = saldo + cantidad;

        // Simulamos trabajo/retardo antes de escribir el saldo
        sleepAleatorio();

        this.saldo = nuevoSaldo;

        // 3. Mostrar el nuevo saldo final.
        System.out.printf("%s ha ingresado %.2f. Saldo final: %.2f%n", nombreHilo, cantidad, saldo);
    }
}
