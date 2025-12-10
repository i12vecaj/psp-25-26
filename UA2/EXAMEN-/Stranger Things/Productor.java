
import java.util.Random;
public class Productor extends Thread {
    private final Buffer buffer;
    private final int eventosAProducir;
    private final Random random;

    public Productor(Buffer buffer, int eventosAProducir) {
        this.buffer = buffer;
        this.eventosAProducir = eventosAProducir;
        this.random = new Random();
    }
 

    @Override
    public void run() {
        String[] eventos = {
            "Demogorgon detectado",
            "Portal inestable",
            "Criatura del Upside Down avistada",
            "Energía oscura detectada",
            "Puerta dimensional abierta"
        };

        for (int i = 0; i < eventosAProducir; i++) {
            String evento = eventos[random.nextInt(eventos.length)];
            try {
                buffer.producir(evento);
                Thread.sleep(random.nextInt(500)); 
        
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}


