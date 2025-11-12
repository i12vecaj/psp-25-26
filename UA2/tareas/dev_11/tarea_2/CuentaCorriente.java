import java.util.Random;

/**
 * Clase que representa una cuenta corriente compartida por varios hilos.
 * Incluye métodos sincronizados para evitar condiciones de carrera.
 */
public class CuentaCorriente {
    // Atributo: saldo de la cuenta
    private double saldo;
    // Generador de números aleatorios para el retardo
    private final Random random = new Random();

    // Constructor: asigna un saldo inicial
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Getter del saldo
    public synchronized double getSaldo() {
        try {
            Thread.sleep(random.nextInt(1750) + 250); // Pausa que simula latencia. 250–2000 ms
        } catch (InterruptedException e) {
            System.err.println("Error en getSaldo(): " + e.getMessage());
        }
        return saldo;
    }

    // Setter del saldo
    public synchronized void setSaldo(double nuevoSaldo) {
        try {
            Thread.sleep(random.nextInt(1750) + 250); // Pausa que simula latencia.
        } catch (InterruptedException e) {
            System.err.println("Error en setSaldo(): " + e.getMessage());
        }
        this.saldo = nuevoSaldo;
    }

    // Método synchronized para ingresar una cantidad
    public synchronized void ingresar(double cantidad, String nombreHilo) {
        System.out.printf("[%s] Intentando ingresar %.2f €...%n", nombreHilo, cantidad);

        double saldoAnterior = getSaldo();
        System.out.printf("[%s] Saldo antes del ingreso: %.2f €%n", nombreHilo, saldoAnterior);

        double nuevoSaldo = saldoAnterior + cantidad;
        setSaldo(nuevoSaldo);

        System.out.printf("[%s] Ingreso completado. Nuevo saldo: %.2f €%n", nombreHilo, nuevoSaldo);
        System.out.println("---------------------------------------------------");
    }
}

