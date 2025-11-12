package tarea_2;

/**
 * Programa principal que crea una cuenta compartida entre varios hilos.
 * Cada hilo representa una persona que realiza un ingreso en la misma cuenta.
 * 
 * FR3: Se utiliza synchronized para asegurar la consistencia del saldo.
 * FR4: Si se elimina synchronized del método ingresarDinero(), se producen
 *      inconsistencias en el saldo final por condiciones de carrera.
 */
public class MainCuenta {
    public static void main(String[] args) {
        System.out.println("=== Simulación de Cuenta Corriente con Hilos ===");

        // Crear cuenta con saldo inicial
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo() + "€");
        System.out.println("--------------------------------------------");

        // Crear varios hilos que comparten la misma cuenta
        HiloIngreso h1 = new HiloIngreso("Cliente 1", cuenta, 500);
        HiloIngreso h2 = new HiloIngreso("Cliente 2", cuenta, 700);
        HiloIngreso h3 = new HiloIngreso("Cliente 3", cuenta, 300);
        HiloIngreso h4 = new HiloIngreso("Cliente 4", cuenta, 1000);

        // Iniciar hilos
        h1.start();
        h2.start();
        h3.start();
        h4.start();

        // Esperar a que todos los hilos terminen
        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar los hilos: " + e.getMessage());
        }

        // Mostrar saldo final
        System.out.println("Saldo final: " + cuenta.getSaldo() + "€");
    }
}
