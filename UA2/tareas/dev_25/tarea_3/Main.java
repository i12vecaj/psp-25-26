public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new newThread());


        //En los siguientes fragmentos recojo el momento de inicio y de cada hilo y su final para ver la diferencia
        long tStart1 = System.currentTimeMillis();
        System.out.println("Tiempo con start() hilo 1: " +tStart1);
        thread1.start();
        thread1.join();
        long tEnd1 = System.currentTimeMillis();
        System.out.println("Tiempo con end() hilo 1: " +tEnd1);

        long tStart2 = System.currentTimeMillis();
        System.out.println("Tiempo con start() hilo 2: " +tStart2);
        new newThread().run();
        long tEnd2 = System.currentTimeMillis();
        System.out.println("Tiempo con end() hilo 2: " +tEnd2);

        System.out.println("Tiempo con start(): " + (tEnd1 - tStart1));
        System.out.println("Tiempo con run():   " + (tEnd2 - tStart2));
    }
}
