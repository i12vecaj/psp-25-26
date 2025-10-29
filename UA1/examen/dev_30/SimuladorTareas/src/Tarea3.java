import java.util.Random;
public class Tarea3 implements Runnable{
    private static final Random randomNumber = new Random();
    @Override
    public void run() {
        System.out.println("Tarea 3 Comenzando a Ejecutarse");
        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(randomNumber.nextInt(2001)+1000);
                System.out.println("[Tarea 3] Ejecutándose en el hilo actual:" + " - Hilo: " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("tarea 3 Finalizada");
    }
}
