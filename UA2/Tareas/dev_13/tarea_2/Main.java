public class Main {
    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(1000);

        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        HiloIngresar h1 = new HiloIngresar(cuenta, 200, "Hilo 1");
        HiloIngresar h2 = new HiloIngresar(cuenta, 150, "Hilo 2");
        HiloIngresar h3 = new HiloIngresar(cuenta, 300, "Hilo 3");

        h1.start();
        h2.start();
        h3.start();

        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar los hilos");
        }

        System.out.println("Saldo final: " + cuenta.getSaldo());
    }
}
