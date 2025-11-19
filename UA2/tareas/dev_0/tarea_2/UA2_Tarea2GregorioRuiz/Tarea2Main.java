/*
  Tarea2Main.java: programa principal que crea una CuentaCorriente y varios hilos que realizan ingresos.
  Ejecuta dos escenarios: usando métodos sincronizados y no sincronizados, y muestra saldos inicial y final.
*/

public class Tarea2Main {
    public static void main(String[] args) {
        System.out.println("Tarea 2 - Programación y sincronización de hilos en Java");
        System.out.println("=== ESCENARIO 1: CON SYNCHRONIZATION ===");
        ejecutarEscenario(true);
        System.out.println("\n=== ESCENARIO 2: SIN SYNCHRONIZATION ===");
        ejecutarEscenario(false);
    }

    private static void ejecutarEscenario(boolean usarSync) {
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.printf("Saldo inicial: %d%n", cuenta.getSaldo());

        IngresadorThread[] hilos = new IngresadorThread[5];
        hilos[0] = new IngresadorThread(cuenta, "Alice", 300, usarSync);
        hilos[1] = new IngresadorThread(cuenta, "Bob", 200, usarSync);
        hilos[2] = new IngresadorThread(cuenta, "Charlie", 500, usarSync);
        hilos[3] = new IngresadorThread(cuenta, "Diana", 100, usarSync);
        hilos[4] = new IngresadorThread(cuenta, "Eve", 400, usarSync);

        for (IngresadorThread t : hilos) t.start();

        for (IngresadorThread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Hilo principal interrumpido durante join");
            }
        }

        System.out.printf("Saldo final (usarSync=%b): %d%n", usarSync, cuenta.getSaldo());
    }
}
