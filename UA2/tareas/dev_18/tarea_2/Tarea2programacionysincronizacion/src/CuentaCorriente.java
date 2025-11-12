/* T2 - Tarea 2 - Programación y sincronización de hilos en Java 2
*FR1
* Nombre: Alberto Nieto Lozano
* */

import java.util.Random;

public class CuentaCorriente {
    private double saldo;
    private final Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    public synchronized void setSaldo(double saldo) {
        dormirAleatorio();
        this.saldo = saldo;
    }

    public synchronized void ingresar(double cantidad, String nombre) {   // Ingresar saldo a través de indicar una cantidad y un nombre
        dormirAleatorio();
        double saldoPrevio = saldo;
        saldo += cantidad; //La cantidad se sumará al saldo
        System.out.println(nombre + " realiza un ingreso de " + cantidad + "€ | Saldo previo: " + saldoPrevio + "€ | Saldo final: " + saldo + "€");
    }

    private void dormirAleatorio() {  // Función para meter un tiempo de sleep en otras funciones
        try {
            Thread.sleep(random.nextInt(1750) + 250); // entre 250 y 2000 ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
