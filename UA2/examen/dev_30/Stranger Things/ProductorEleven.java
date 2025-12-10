import java.util.Random;

public class ProductorEleven implements Runnable{
    private static final Random RANDOM = new Random();
    private final Buffer buffer;
    public ProductorEleven(Buffer buffer) {
        this.buffer = buffer;
    }
    @Override
    public void run() {
        System.out.println("Eleven Genera Evento");
        for (int i = 0; i <= 10; i++) {
            buffer.detectEvent();
            try {
                int waitingTime = RANDOM.nextInt(100)+30;
                System.out.println(Thread.currentThread().getName()+", Cerrando Portal, Tiempo de Procesamiento: "+waitingTime+"ms");
                Thread.sleep(waitingTime);
            }catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName() + "Interrupted");
            }
        }
    }
}
