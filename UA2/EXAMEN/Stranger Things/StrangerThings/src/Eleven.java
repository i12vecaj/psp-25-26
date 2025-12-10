import java.util.Random;

public class Eleven extends Thread{

    private final Laboratorio laboratorio;
    private int eventosrealizados = 0;

    public Eleven (String nombre, Laboratorio laboratorio){
        super(nombre);
        this.laboratorio = laboratorio;
    }

    public void run() {
        while (true) {
            try {
                int tiempoPreparacion = new Random().nextInt(1500) + 1000;
                System.out.println(getName() + " realizando evento... (" + (tiempoPreparacion/1000) + "s)");
                System.out.println("==================================================\n");
                Thread.sleep(tiempoPreparacion);

                Evento evento = new Evento(getName());
                laboratorio.producirEvento(evento);
                eventosrealizados++;

                System.out.println(getName() + " lleva " + eventosrealizados + " eventos realizados");
                System.out.println("==================================================\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
