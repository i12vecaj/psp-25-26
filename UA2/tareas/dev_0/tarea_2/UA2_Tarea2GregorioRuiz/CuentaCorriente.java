/*
  CuentaCorriente.java: clase que representa una cuenta con saldo y métodos para obtener, establecer
  y realizar ingresos (versiones sincronizada y no sincronizada). Los métodos get/set introducen sleeps aleatorios.
*/

import java.util.Random;

public class CuentaCorriente {
    private int saldo;
    private final Random rnd = new Random();

    public CuentaCorriente(int saldoInicial) {
        if (saldoInicial < 0) throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        this.saldo = saldoInicial;
    }

    public int getSaldo() {
        sleepRandom();
        return saldo;
    }

    public void setSaldo(int nuevoSaldo) {
        if (nuevoSaldo < 0) throw new IllegalArgumentException("El saldo no puede ser negativo");
        sleepRandom();
        this.saldo = nuevoSaldo;
    }

    public synchronized void ingresarSync(String quien, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad a ingresar debe ser mayor que cero");
        int previo = getSaldo();
        System.out.printf("Antes: %d | Ingreso: %d | Realiza: %s%n", previo, cantidad, quien);
        int nuevo = previo + cantidad;
        setSaldo(nuevo);
        System.out.printf("Después: %d | Realizado por: %s%n", getSaldo(), quien);
    }

    public void ingresarNoSync(String quien, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad a ingresar debe ser mayor que cero");
        int previo = getSaldo();
        System.out.printf("Antes: %d | Ingreso: %d | Realiza: %s%n", previo, cantidad, quien);
        int nuevo = previo + cantidad;
        setSaldo(nuevo);
        System.out.printf("Después: %d | Realizado por: %s%n", getSaldo(), quien);
    }

    private void sleepRandom() {
        int min = 250;
        int max = 2000;
        int tiempo = min + rnd.nextInt(max - min + 1);
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido durante sleep");
        }
    }
}