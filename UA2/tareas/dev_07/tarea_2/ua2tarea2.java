public class ua2tarea2 {
    public static void main(String[] args) {
        try {
            cuentabanco cuenta = new cuentabanco(300);
            System.out.println("Saldo inicial: " + cuenta.getSaldo());

            Thread hilo1 = new Hilo("Hilo1", cuenta, 300);
            Thread hilo2 = new Hilo("Hilo2", cuenta, 450);
            Thread hilo3 = new Hilo("Hilo3", cuenta, 100);
            
            hilo1.start();
            hilo2.start();
            hilo3.start();

            hilo1.join();
            hilo2.join();
            hilo3.join();

            System.out.println("Tienes en total: " + cuenta.getSaldo());

        } catch (InterruptedException e) {
            System.out.println("Error en la ejecución de los hilos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
}