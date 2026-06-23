import static java.lang.Thread.sleep;

public class Cocinar implements Runnable{

    @Override
    public void run() {
        try {
            sleep(1000);
            System.out.println("Comenzando a Cocinar...");
            sleep(1000);
            System.out.println("Comida preparada.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
