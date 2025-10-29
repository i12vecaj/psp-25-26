import java.util.Random;
public class Tarea1 implements Runnable {
private static final Random randomNumber = new Random();
    @Override
    public void run() {
        System.out.println("Tarea 1 Comenzando a Ejecutarse");
        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(randomNumber.nextInt(2001)+1000);
                System.out.println("[Tarea 1] Ejecutándose en el hilo actual:" + " - Hilo: " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("tarea 1 Finalizada");
    }
}
