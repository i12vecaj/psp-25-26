import java.util.Random;

public class Lavar_la_Ropa implements Runnable {






    @Override
    public void run() {

        for (int i = 0; i < 1; i++) {
            System.out.println("Se esta lavando la ropa ");

            try {
                Random rand = new Random();
                int tiempoespera = rand.nextInt(1000 - 250) + 250;

                Thread.sleep(tiempoespera);
                System.out.println("");

                System.out.println("<< Todo oliendo a rosas y lavanda >>");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
