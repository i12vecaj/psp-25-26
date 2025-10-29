import java.util.Random;

public class LavarRopa implements Runnable{


    @Override
    public void run() {
        Random randomNumbers = new Random();
        System.out.println("[Lavar ropa] Comenzando tarea...");
        try {
            Thread.sleep(randomNumbers.nextInt(2000) + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[Lavar ropa] Tarea finalizada.");
    }
}
