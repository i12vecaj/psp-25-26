import java.util.Random;
public class Cocinar implements Runnable{
    private static final Random randonNumber = new Random();
    @Override
    public void run() {
        System.out.println("Iniciando Tarea: Cocinar");
        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(randonNumber.nextInt(3001)+1000);
                System.out.println("[Cocinando]: Hilo Actual: " + "- Hilo: " + i);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        System.out.println("Tarea Finalizada: Cocinar");
    }
}