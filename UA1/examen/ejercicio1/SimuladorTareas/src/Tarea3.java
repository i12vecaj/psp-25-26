public class Tarea3 implements Runnable {

    int tarea; // Numero de interaciones que va a tener el hilo

    public Tarea3(int num) {
        this.tarea = num;
    }

    //Creamoss la interación

    @Override
    public void run() {

        for (int i = 0; i < tarea; i++) {
            System.out.println("La iteración " + i + " Tarea 3");
        }
        try {
            //Tiempo que está dormido el proceso
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String toString() {
        return "Tarea2: " + "num: " + tarea;
    }
}


