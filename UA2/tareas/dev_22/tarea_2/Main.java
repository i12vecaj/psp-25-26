/**
 *  * Autor: Jose Antonio Roda Donoso
 *  * Curso: 2º DAM
 *  * Unidad: UA2 - Tarea 2 Main
 *
 * Crea una cuenta corriente con saldo inicial y varios hilos
 * que comparten dicha cuenta para realizar ingresos.
 *
 * - Con synchronized el resultado final es correcto
 * - Sin el synchronized el resultado final es incorrecto
 *
 * En el FR3, los hilos entraban uno por uno y el saldo final siempre salía correcto.
 * En el FR4, al quitar el synchronized, los hilos podían modificar el saldo al mismo tiempo
 */

public class Main {
    public static void main(String[] args) {
        // Crear la cuenta con saldo inicial
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo() + " €");
        System.out.println(" ");

        // Crear varios hilos que comparten la misma cuenta
        HiloIngreso h1 = new HiloIngreso("Ana", cuenta, 300);
        HiloIngreso h2 = new HiloIngreso("Pedro", cuenta, 500);
        HiloIngreso h3 = new HiloIngreso("Lucía", cuenta, 200);
        HiloIngreso h4 = new HiloIngreso("Carlos", cuenta, 400);

        // Iniciar los hilos
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
            System.err.println("Error esperando hilos: " + e.getMessage());
        }

        // Mostrar el saldo final
        System.out.println("Saldo final en la cuenta: " + cuenta.getSaldo() + " €");
    }
}
