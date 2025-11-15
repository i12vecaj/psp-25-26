package U2;

import java.util.Random;
/* En este programa lo que hacemos es crear una clase llamada CuentaCorriente con su contructor que tiene como atributo el saldo de dicha cuenta
    luego creamos el metodo  getSaldo que es para poder obtener su saldo y también para modificar duhco saldo creamos el metodo setSaldo
    y por ultimo cremaos el metodo ingresarSaldo el cual lo que hace es mostrar el saldo que hay en la cuenta sumarlo la cantidad que el usuario 
    quiera y mostrar el saldo final que hay en la cuenta diciendo también quien fue el que lo hizo
 */
public class CuentaCorriente {
    private double saldo;
    private Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    // Getter del saldo (con sleep aleatorio)
    public double getSaldo() {
        try {
            Thread.sleep(random.nextInt(500)); // pausa entre 0–500ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido al obtener saldo");
        }
        return saldo;
    }

    // Setter del saldo (con sleep aleatorio)
    public void setSaldo(double nuevoSaldo) {
        if (nuevoSaldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
        try {
            Thread.sleep(random.nextInt(500)); // pausa entre 0–500ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido al establecer saldo");
        }
        this.saldo = nuevoSaldo;
    }

    // Método para ingresar una cantidad
    public void ingresar(String nombreHilo, double cantidad) {
        if (nombreHilo == null || nombreHilo.isBlank()) {
            throw new IllegalArgumentException("El nombre del hilo no es válido");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad a ingresar no puede ser negativa");
        }

        System.out.println("Operación realizada por: " + nombreHilo + " | Saldo Previo: " + saldo);
        saldo += cantidad;
        System.out.println("Saldo Final: " + saldo);
    }
}
