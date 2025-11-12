package Tarea2;

import java.util.Random;

public class CuentaCorriente {
    //Atributo principal
    private double saldo;
    //utilizo la clase random para despues generar un milisegundo random entre x milisegundos
    private final Random random = new Random();

    public CuentaCorriente(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        try {
            Thread.sleep(random.nextInt(1751) + 250); //simula latencia, y hace de 250-2000
        } catch (InterruptedException e) {
            System.err.println("Error en getSaldo: " + e.getMessage());
        }
        return saldo;
    }

    public void setSaldo(double saldo) {
        try {
            Thread.sleep(random.nextInt(1751) + 250); //simula latencia, y hace de 250-2000
        } catch (InterruptedException e) {
            System.err.println("Error en setSaldo: " + e.getMessage());
        }
        this.saldo = saldo;
    }


    public synchronized void ingresarDinero(String nombreHilo, double cantidad) {
        //Para ver el saldo previo
        double saldoPrevio = getSaldo();

        //Sumar la cantidad al sueldo
        double saldoFinal= saldoPrevio+cantidad;

        //Modifico el saldo por el saldo final
        setSaldo(saldoFinal);

        //Imprimo por pantalla el saldo precio, el saldo final y el nombre del hilo
        System.out.println("-Saldo previo: "+saldoPrevio+"\n-Saldo final: "+saldoFinal+"\n-Hilo que realiza el cambio: "+nombreHilo);
    }

}