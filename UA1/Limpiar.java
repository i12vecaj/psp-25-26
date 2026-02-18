import static java.lang.Thread.sleep;

public class Limpiar implements Runnable{

    @Override
    public void run() {
        try {
            System.out.println("Comenzando a Limpiar...");
            sleep(500);
            System.out.println("Limpieza terminada.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
