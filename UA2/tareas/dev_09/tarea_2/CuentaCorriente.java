/**
 * Autor David Alberto Cruz Barranco
 * Fecha 12/11/2025
 * UA 2
 * Act 2
 **/

import java.util.Random;

public class CuentaCorriente {
    private double saldo;
    private Random random = new Random();

    // Constructor con valor inicial del saldo
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Getter con retardo aleatorio
    public synchronized double getSaldo() {
        try {
            Thread.sleep(250 + random.nextInt(1750));
            // entre 250 y 2000 ms
        } catch (InterruptedException e) {
            System.out.println("Error en getSaldo(): " + e.getMessage());
        }
        return saldo;
    }

    // Setter con retardo aleatorio
    public synchronized void setSaldo(double saldo) {
        try {
            Thread.sleep(250 + random.nextInt(1750));
        } catch (InterruptedException e) {
            System.out.println("Error en setSaldo(): " + e.getMessage());
        }
        this.saldo = saldo;
    }

    public synchronized void addSaldo(double cantidad, String nombre) {
        try {
            double saldoPrevio = getSaldo();
            System.out.println(nombre + " va a ingresar " + cantidad + "€ (saldo previo: " + saldoPrevio + "€)");
            double nuevoSaldo = saldoPrevio + cantidad;

            // Simula retardo
            Thread.sleep(250 + random.nextInt(1750));

            setSaldo(nuevoSaldo);
            System.out.println(nombre + " ha ingresado " + cantidad + "€. Nuevo saldo: " + nuevoSaldo + "€");
        } catch (InterruptedException e) {
            System.out.println("Error en addSaldo(): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}
