import java.util.Random;

public class ConsumidorLaboratorio implements Runnable{
    private static final Random RANDOM = new Random();
    private final Buffer buffer;
    public ConsumidorLaboratorio(Buffer buffer) {
        this.buffer = buffer;
    }
    @Override
    public void run() {
        System.out.println("Laboratorio Detecta Evento");
        for (int i = 0; i <= 10; i++) {
            buffer.processEvent();
            try {
                int waitingTime = RANDOM.nextInt(200)+100;
                System.out.println(Thread.currentThread().getName()+", Procesa un Evento, Tiempo de Procesamiento: "+waitingTime+"ms");
                Thread.sleep(waitingTime);
            }catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName() + "Interrupted");
            }
        }
    }
}
