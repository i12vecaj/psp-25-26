import java.util.Random;
public class Limpiar implements Runnable{
    private static final Random randonNumber = new Random();
    @Override
    public void run() {
        System.out.println("Iniciando Tarea: Limpiar");
        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(randonNumber.nextInt(3001)+1000);
                System.out.println("[Limpiando]: Hilo Actual: " + "- Hilo: " + i);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        System.out.println("Tarea Finalizada: Limpiar");
    }
}