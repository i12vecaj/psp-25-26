package tarea_2;

import java.util.Random;

/**
 * Clase que representa una cuenta corriente con un saldo.
 * Incluye métodos para obtener, modificar e ingresar dinero.
 * Los métodos simulan retardos con sleep() aleatorio para observar
 */
public class CuentaCorriente {
    private double saldo;
    private Random random = new Random();

    // Constructor
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Getter con retardo aleatorio
    public double getSaldo() {
        try {
            Thread.sleep(random.nextInt(1750 + 1) + 250); // entre 250 y 2000 ms
        } catch (InterruptedException e) {
            System.err.println("Error al obtener el saldo: " + e.getMessage());
        }
        return saldo;
    }

    // Setter con retardo aleatorio
    public void setSaldo(double saldo) {
        try {
            Thread.sleep(random.nextInt(1750 + 1) + 250);
        } catch (InterruptedException e) {
            System.err.println("Error al modificar el saldo: " + e.getMessage());
        }
        this.saldo = saldo;
    }

    /**
     * Método synchronized para añadir saldo a la cuenta.
     * Evita condiciones de carrera cuando varios hilos acceden al saldo.
     * 
     * @param cantidad cantidad de dinero a ingresar
     * @param nombre nombre del hilo que realiza el ingreso
     */
    public synchronized void ingresarDinero(double cantidad, String nombre) {
        try {
            System.out.println(nombre + " va a ingresar " + cantidad + "€");
            double saldoPrevio = getSaldo();
            System.out.println("Saldo antes del ingreso: " + saldoPrevio + "€");

            double nuevoSaldo = saldoPrevio + cantidad;
            setSaldo(nuevoSaldo);

            System.out.println("Saldo después del ingreso por " + nombre + ": " + nuevoSaldo + "€");
            System.out.println("--------------------------------------------");
        } catch (Exception e) {
            System.err.println("Error en el ingreso de " + nombre + ": " + e.getMessage());
        }
    }
}
