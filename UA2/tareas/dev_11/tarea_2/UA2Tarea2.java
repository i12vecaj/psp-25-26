public class UA2Tarea2 {
    public static void main(String[] args) {
        System.out.println("Simulación de Cuenta Corriente Compartida");

        // Crear cuenta con saldo inicial
        CuentaCorriente cuenta = new CuentaCorriente(1000.0);
        System.out.printf("Saldo inicial: %.2f €%n", cuenta.getSaldo());

        // Crear hilos que comparten la misma cuenta
        Thread h1 = new HiloIngreso(cuenta, 500, "Hilo 1");
        Thread h2 = new HiloIngreso(cuenta, 300, "Hilo 2");
        Thread h3 = new HiloIngreso(cuenta, 200, "Hilo 3");

        // Lanzar hilos
        h1.start();
        h2.start();
        h3.start();

        // Esperar a que terminen
        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar a los hilos: " + e.getMessage());
        }

        // Mostrar saldo final
        System.out.printf("Saldo final: %.2f €%n", cuenta.getSaldo());
        System.out.println("Fin de la simulación");
    }
}
