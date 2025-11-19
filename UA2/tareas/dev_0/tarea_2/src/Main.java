public class Main {
    public static void main(String[] args) {
        Cuenta cuenta = new Cuenta(1000);

        System.out.println("Saldo: " + cuenta.getSaldo());

        Ingreso h1 = new Ingreso("Hilo-1", cuenta, 200);
        Ingreso h2 = new Ingreso("Hilo-2", cuenta, 150);
        Ingreso h3 = new Ingreso("Hilo-3", cuenta, 500);

        h1.start();
        h2.start();
        h3.start();

        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Final: " + cuenta.getSaldo());
    }

}