import java.util.Random;

public class CuentaCorriente {
    private double saldo;
    private Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Metodo getter con sleep aleatorio
    public double getSaldo() {
        try {
            // Duerme el hilo entre 250ms y 2000ms
            Thread.sleep(random.nextInt(1750) + 250);
        } catch (InterruptedException e) {
            // Control de errores básico
            System.err.println("Error en getSaldo: Hilo interrumpido.");
        }
        return saldo;
    }

    // Metodo setter con sleep aleatorio
    public void setSaldo(double nuevoSaldo) {
        try {
            // Duerme el hilo entre 250ms y 2000ms
            Thread.sleep(random.nextInt(1750) + 250);
        } catch (InterruptedException e) {
            // Control de errores básico
            System.err.println("Error en setSaldo: Hilo interrumpido.");

        }
        this.saldo = nuevoSaldo;
    }

    // Metodo para añadir saldo, definido como synchronized.
    public synchronized void ingresar(String nombreHilo, double cantidad) {
        // Implementación de control de errores básico
        if (cantidad <= 0) {
            System.err.printf("[%s] ERROR: Intento de ingreso con cantidad no positiva (%.2f).%n", nombreHilo, cantidad);
            return;
        }
        // Inicio de la Sección Crítica
        double saldoAnterior = getSaldo(); // Lee el saldo

        // Muestra el estado previo del saldo y quién realiza el ingreso
        System.out.printf("[%s] INGRESANDO | Saldo anterior: %.2f | Cantidad a ingresar: %.2f%n", nombreHilo, saldoAnterior, cantidad);

        double saldoNuevo = saldoAnterior + cantidad;
        setSaldo(saldoNuevo); // Actualiza el saldo

        // Muestra el estado final
        System.out.printf("[%s] FINALIZADO | Saldo final: %.2f%n", nombreHilo, saldoNuevo);
    }
}