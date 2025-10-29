public class Main {
    public static void main(String[] args) {
        Thread lavarRopaThread1 = new Thread(new LavarRopa());
        lavarRopaThread1.start();

        Thread cocinarThread2 = new Thread(new Cocinar());
        cocinarThread2.start();

        Thread limpiarThread3 = new Thread(new Limpiar());
        limpiarThread3.start();
    }
}