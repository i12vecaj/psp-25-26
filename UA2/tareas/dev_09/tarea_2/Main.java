/**
 * Autor David Alberto Cruz Barranco
 * Fecha 12/11/2025
 * UA 2
 * Act 2
 **/

/**
 * Diferencia entre el punto 3 y 4:
 * El uso de 'synchronized' garantiza la integridad de los datos cuando varios hilos
 * acceden a un mismo recurso compartido. Sin este control, el programa pierde coherencia
 * y los resultados finales pueden variar en cada ejecución debido a interferencias entre hilos.
 **/

public class Main {
    public static void main(String[] args) {
        // FR3: Crear la cuenta con saldo inicial
        CuentaCorriente cuenta = new CuentaCorriente(1000.0);
        System.out.println("Saldo inicial: " + cuenta.getSaldo() + "€");

        // Crear varios hilos que compartan la misma cuenta
        HiloIngreso h1 = new HiloIngreso(cuenta, "Cliente-1", 500);
        HiloIngreso h2 = new HiloIngreso(cuenta, "Cliente-2", 300);
        HiloIngreso h3 = new HiloIngreso(cuenta, "Cliente-3", 700);

        // Iniciar los hilos
        h1.start();
        h2.start();
        h3.start();

        // Esperar a que terminen
        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar hilos: " + e.getMessage());
        }

        // Mostrar saldo final
        System.out.println("\nSaldo final: " + cuenta.getSaldo() + "€");
    }
}


class HiloIngreso extends Thread {
    private CuentaCorriente cuenta;
    private double cantidad;

    public HiloIngreso(CuentaCorriente cuenta, String nombre, double cantidad) {
        super(nombre);
        // establece el nombre del hilo
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.addSaldo(cantidad, getName());
    }
}