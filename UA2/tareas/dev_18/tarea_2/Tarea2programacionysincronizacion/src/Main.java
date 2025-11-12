/* T2 - Tarea 2 - Programación y sincronización de hilos en Java 2
 *FR3
 * Nombre: Alberto Nieto Lozano
 **/

public class Main {
    public static void main(String[] args) {
        // Crear cuenta con saldo inicial
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo() + "€");

        // Crear varios hilos que comparten la misma cuenta
        HiloIngreso h1 = new HiloIngreso(cuenta, 200, "Hilo 1");
        HiloIngreso h2 = new HiloIngreso(cuenta, 150, "Hilo 2");
        HiloIngreso h3 = new HiloIngreso(cuenta, 300, "Hilo 3");
        HiloIngreso h4 = new HiloIngreso(cuenta, 500, "Hilo 4");

        // Iniciar los hilos
        h1.start();
        h2.start();
        h3.start();
        h4.start();

        // Esperar a que todos los hilos terminen (necesario para mostrar el saldo final)
        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Mostrar saldo final
        System.out.println("\nSaldo final: " + cuenta.getSaldo() + "€");
    }
}
