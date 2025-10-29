import java.util.Random;

public class Limpiar implements Runnable{



    @Override
    public void run() {
        Random randomNumbers = new Random();
        System.out.println("[Limpiar] Comenzando tarea...");
        try {
            Thread.sleep(randomNumbers.nextInt(2000) + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[Limpiar] Tarea finalizada.");
    }
}
