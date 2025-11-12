import java.util.Random;

public class CuentaCorriente {

    private double saldo;
    private Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    public synchronized void setSaldo(double nuevoSaldo) {
        dormirAleatorio();
        this.saldo = nuevoSaldo;
    }

    public synchronized void ingresar(double cantidad, String nombrePersona) {
        System.out.println(nombrePersona + " va a ingresar " + cantidad + "€...");
        System.out.println("Saldo anterior: " + saldo + "€");

        dormirAleatorio();

        saldo += cantidad;

        System.out.println("Saldo final tras ingreso de " + nombrePersona + ": " + saldo + "€\n");
    }

    private void dormirAleatorio() {
        try {
            int tiempo = 250 + random.nextInt(1751); // entre 250 y 2000 ms
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo fue interrumpido.");
        }
    }

    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(1000.0);

        Thread t1 = new Thread(() -> cuenta.ingresar(500, "Ana"));
        Thread t2 = new Thread(() -> cuenta.ingresar(300, "Luis"));
        Thread t3 = new Thread(() -> cuenta.ingresar(200, "Marta"));

        t1.start();
        t2.start();
        t3.start();
    }
}
