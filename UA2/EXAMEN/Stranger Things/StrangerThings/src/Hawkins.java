import java.util.Random;

public class Hawkins extends Thread {
    private final Laboratorio laboratorio;
    private int Eventosprocesados = 0;

    public Hawkins(String nombre, Laboratorio laboratorio) {
        super(nombre);
        this.laboratorio = laboratorio;
    }

    public void run() {
        System.out.println(getName() + " va a procesar un evento");
        System.out.println("==================================================\n");

        while (true) {
            try {
                Evento evento = laboratorio.consumirEvento(getName());

                int tiempoConsumiendo = new Random().nextInt(2000) + 1000;
                System.out.println(getName() + " está procesando " + evento.getTipo() + "...");
                System.out.println("==================================================\n");
                Thread.sleep(tiempoConsumiendo);

                Eventosprocesados++;
                System.out.println(getName() + " ha procesado " + Eventosprocesados);
                System.out.println("==================================================\n");

                Thread.sleep(new Random().nextInt(1000) + 500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
