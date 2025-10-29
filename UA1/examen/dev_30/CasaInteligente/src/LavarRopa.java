import java.util.Random;
public class LavarRopa implements Runnable{
    private static final Random randonNumer = new Random();
    @Override
    public void run() {
        System.out.println("Iniciando Tarea: Lavar Ropa");
        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(randonNumer.nextInt(3001)+1000);
                System.out.println("[Lavando Ropa]: Hilo Actual:  " + "- Hilo: " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Tarea Finalizada: Lavar Ropa");
    }
}