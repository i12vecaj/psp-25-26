/*
a) Crea una clase interna llamada Tarea que implemente la interfaz Runnable.
Cada tarea debe tener un nombre (por ejemplo, "Tarea 1", "Tarea 2", "Tarea 3")
que se reciba por parámetro en el constructor.
(1 punto)
 */

public class Tarea1 implements Runnable{

    int tarea; // Numero de iteraciones que va a tener el hilo


    public Tarea1(int tarea) {
        this.tarea = tarea;
    }


    //Creamoss la interación
    @Override
    public void run() {

        for(int i = 0; i < tarea; i++){
            System.out.println("La iteración " + i + " Tarea 1");
        }
        try{
            //Modificamos el tiempo que el proceso esta dormido
            Thread.sleep(1500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "tarea: " + tarea;
    }
}
