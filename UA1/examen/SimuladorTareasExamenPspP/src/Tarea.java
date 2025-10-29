import java.util.Random;


public class Tarea implements Runnable {

    private int id;
    private String nombre;

    public Tarea(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    @Override
    public void run() {

        for(int i=0; i<3; i++){
        System.out.println(nombre +" ejecutandose en el hilo: "+id);
        
        try {
            Random rand = new Random();
            int tiempoespera = rand.nextInt(1500-500) + 500;

           Thread.sleep(tiempoespera);
           System.out.println("");

           System.out.println(nombre +" ha terminado su ejecución en el hilo: "+id);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    }
}
