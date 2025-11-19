public class Main {
    public static void main(String[] args) {
        try {
            // La cuenta es compartida entre todos
            CuentaCorriente cuenta = new CuentaCorriente(50);
            System.out.println("Saldo inicial: " + cuenta.getSaldo() + " €");

            HiloIngreso hilo1 = new HiloIngreso("Hilo1", cuenta, 1200.50);
            HiloIngreso hilo2 = new HiloIngreso("Hilo2", cuenta, 1500.96);
            HiloIngreso hilo3 = new HiloIngreso("Hilo3", cuenta, 2400.23);

            hilo1.start();
            hilo2.start();
            hilo3.start();

            // Join para que terminen los hilos primero
            hilo1.join();
            hilo2.join();
            hilo3.join();

            System.out.println("Saldo final: " + cuenta.getSaldo());

        } catch (InterruptedException e) {
            System.out.println("Error en la ejecución de los hilos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
}
