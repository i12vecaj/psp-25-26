public class Main {

    public static void main(String[] args) {

        // 1. Creo una nueva cuenta que será compartida con todos los hilos para así ir pudiendo hacer cambios sobre la misma
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo() + "\n");

        // 2. Creo varios hilos usando la misma cuenta
        HiloIngreso h1 = new HiloIngreso("Hilo-1", cuenta, 300);
        HiloIngreso h2 = new HiloIngreso("Hilo-2", cuenta, 500);
        HiloIngreso h3 = new HiloIngreso("Hilo-3", cuenta, 250);
        HiloIngreso h4 = new HiloIngreso("Hilo-4", cuenta, 450);

        // 3. Inicio todos los hilos
        h1.start();
        h2.start();
        h3.start();
        h4.start();

        // 4. Espero a que todos los hilos finalicen
        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        //5. Imprimo el balance final
        System.out.println("\nSaldo final de la cuenta: " + cuenta.getSaldo());
    }
}
