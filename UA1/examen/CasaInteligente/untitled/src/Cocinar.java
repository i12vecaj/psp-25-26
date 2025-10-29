import java.util.Random;

public class Cocinar implements Runnable {




    @Override
    public void run() {


        for (int i = 0; i < 1; i++) {
            System.out.println("La comida se esta haciendo, hmmm lentejas");

            try {
                Random rand = new Random();
                int tiempoespera = rand.nextInt(1000 - 250) + 250;

                Thread.sleep(tiempoespera);
                System.out.println("");

                System.out.println("<< El plato ya está vacío ;( >>");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}

}