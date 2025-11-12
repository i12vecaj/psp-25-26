/**
 * Autor: Jose Antonio Roda Donoso
 * Curso: 2º DAM
 * Unidad: UA2 - Tarea 2 HiloIngreso
 *
 * Representa una cuenta corriente con un saldo y permite que varios hilos
 * realicen ingresos sobre la misma cuenta.
 */

import java.util.Random;

public class CuentaCorriente {
    // Atributo
    private double saldo;
    private Random random = new Random();

    // Constructor
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Getter del saldo en aleotorio
    public double getSaldo() {
        try {
            Thread.sleep(random.nextInt(1751) + 250); // Pausa entre 250 y 2000 ms
        } catch (InterruptedException e) {
            System.err.println("Error en getSaldo: " + e.getMessage());
        }
        return saldo;
    }

    // Setter del saldo en aleotorio
    public void setSaldo(double nuevoSaldo) {
        try {
            Thread.sleep(random.nextInt(1751) + 250);
        } catch (InterruptedException e) {
            System.err.println("Error en setSaldo: " + e.getMessage());
        }
        this.saldo = nuevoSaldo;
    }

    // Método para ingresar una cantidad
    public synchronized void ingresar(String nombreHilo, double cantidad) {
        try {
            System.out.println(nombreHilo + " intenta ingresar " + cantidad + " €");
            double saldoPrevio = getSaldo();
            System.out.println("Saldo previo: " + saldoPrevio + " €");

            double nuevoSaldo = saldoPrevio + cantidad;

            Thread.sleep(random.nextInt(1751) + 250);

            setSaldo(nuevoSaldo);
            System.out.println(nombreHilo + " ha ingresado " + cantidad + " €. Saldo final: " + nuevoSaldo + " €");
            System.out.println(" ");
        } catch (InterruptedException e) {
            System.err.println("Error durante el ingreso: " + e.getMessage());
        }
    }
}
