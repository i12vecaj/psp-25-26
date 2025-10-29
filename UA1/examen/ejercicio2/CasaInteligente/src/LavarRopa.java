public class LavarRopa implements Runnable {

    int numTarea;


    public LavarRopa(int numTarea) {
        this.numTarea = numTarea;
    }

    @Override
    public void run() {

        for (int i = 0; i < numTarea; i++) {
            if (i == 0) {
                System.out.println("Lavar Ropa " + i + " Comenzando Tarea");
            } else if (i < numTarea) {
                System.out.println("Lavar Ropa: " + i + " Realiando Lavar Ropa");
            }
            try {
                //Modificamos el tiempo que el proceso esta dormido
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Lavar Ropa finalizado");


    }

    @Override
    public String toString() {
        return "Lavar Ropa:" + "numTarea: " + numTarea + "Ha terminado ";
    }
}
