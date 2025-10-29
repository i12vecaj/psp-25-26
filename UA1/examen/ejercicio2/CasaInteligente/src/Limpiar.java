public class Limpiar implements Runnable {


    int numTarea;


    public Limpiar(int numTarea) {
        this.numTarea = numTarea;
    }

    @Override
    public void run() {

        for (int i = 0; i < numTarea; i++) {
            if (i == 0) {
                System.out.println("Limpiando " + i + " Comenzando Tarea");
            } else if (i < numTarea) {
                System.out.println("Limpiando: " + i + " Realiando Limpiando");
            } else if ( numTarea == 5) {
                System.out.println("Limpiando " + i + " Tarea Terminada");
            }
            try {
                //Modificamos el tiempo que el proceso esta dormido
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Ha terminado de Limpiando Tarea");


    }

    @Override
    public String toString() {
        return "Cocinar: " + "numTarea: " + numTarea + "Ha terminado ";
    }
}


