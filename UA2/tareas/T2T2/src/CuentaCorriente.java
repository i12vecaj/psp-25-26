import java.util.Random;

class CuentaCorriente {
    private double saldo;
    private Random random;
    
    public CuentaCorriente(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
        this.random = new Random();
    }
    
    public synchronized double getSaldo() {
        try {
            int sleepTime = 250 + random.nextInt(1751);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println("Error: Hilo interrumpido en getSaldo() - " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return saldo;
    }
    
    public synchronized void setSaldo(double nuevoSaldo) {
        if (nuevoSaldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
        try {
            int sleepTime = 250 + random.nextInt(1751);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println("Error: Hilo interrumpido en setSaldo() - " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        this.saldo = nuevoSaldo;
    }
    
    public synchronized void ingresarDinero(double cantidad, String nombreHilo) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser positiva");
        }
        
        try {
            double saldoPrevio = this.saldo;
            
            int sleepTime = 250 + random.nextInt(1751);
            Thread.sleep(sleepTime);
            
            this.saldo += cantidad;
            double saldoFinal = this.saldo;
            
            System.out.printf("[%s] Ingreso de %.2f€ | Saldo previo: %.2f€ | Saldo final: %.2f€%n",
                    nombreHilo, cantidad, saldoPrevio, saldoFinal);
            
        } catch (InterruptedException e) {
            System.err.println("Error: Hilo interrumpido durante ingreso - " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}