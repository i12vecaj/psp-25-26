import java.util.Random;

public class Cocinar implements Runnable{



    @Override
    public void run() {
        Random randomNumbers = new Random();
        System.out.println("[Cocinar] Comenzando tarea...");
        try {
            Thread.sleep(randomNumbers.nextInt(2000) + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[Cocinar] Tarea finalizada.");
    }
}
