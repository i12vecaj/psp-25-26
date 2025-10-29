public class Cocinar implements Runnable {




    int numTarea;


    public Cocinar(int numTarea) {
        this.numTarea = numTarea;
    }

    @Override
    public void run() {

        for (int i = 0; i < numTarea; i++) {
            if (i == 0) {
                System.out.println("Cocinar " + i + " Comenzando Tarea");
            } else if (i < numTarea) {
                System.out.println("Cocinar: " + i + " Realiando Cocinar");
            }
            try {
                //Modificamos el tiempo que el proceso esta dormido
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        System.out.println("Ha termiando de Cocinar");

    }

    @Override
    public String toString() {
        return "Cocinar: " + "numTarea: " + numTarea + "Ha terminado ";
    }
}


