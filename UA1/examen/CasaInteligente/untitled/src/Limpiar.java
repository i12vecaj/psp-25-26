import java.util.Random;

public class Limpiar implements Runnable {


    @Override
    public void run() {

        for (int i = 0; i < 1; i++) {
            System.out.println("Se esta limpiando la casa");

            try {
                Random rand = new Random();
                int tiempoespera = rand.nextInt(1000 - 250) + 250;

                Thread.sleep(tiempoespera);
                System.out.println("");

                System.out.println("<< La casa terminó de limpiiarse >>");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
