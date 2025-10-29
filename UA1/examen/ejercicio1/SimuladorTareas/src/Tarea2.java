public class Tarea2 implements Runnable {

    int tarea;  // Numero de iteraciones que va a tener el hilo

    public Tarea2(int num) {
        this.tarea = num;
    }

    //Creamoss la interación

    @Override
    public void run() {

        for (int i = 0; i < tarea; i++) {
            System.out.println("La iteración " + i + " Tarea 2");
        }
        try {
            //Modificamos el tiempo que esta dormido el hilo
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String toString() {
        return "Tarea2: " + "num: " + tarea;
    }
}
