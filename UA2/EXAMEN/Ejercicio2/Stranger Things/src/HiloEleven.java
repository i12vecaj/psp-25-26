import java.util.Random;

public class HiloEleven extends Thread {
    private final String nombre;
    private final Pueblo pueblo;
    private int totalProducidas = 0;

    public HiloEleven(String nombre, Pueblo pueblo) {
        this.nombre = nombre;
        this.pueblo = pueblo;
    }

    @Override
    public void run() {
        System.out.println(nombre + " ha comenzado su turno");
        System.out.println("==================================================\n");

        // Bucle infinito: Eleven produce eventos continuamente
        while (true) {
            try {
                // Simular tiempo de Eleven preparando un evento
                int tiempoPreparacion = new Random().nextInt(1500) + 1000;
                System.out.println( nombre + " preparando evento... (" + (tiempoPreparacion/1000) + "s)");
                System.out.println("==================================================\n");
                Thread.sleep(tiempoPreparacion);

                // Crear y producir el evento
                Eventos evento = new Eventos(nombre);
                pueblo.producirEvento(evento);
                totalProducidas++;

                System.out.println( nombre + " lleva " + totalProducidas + " eventos producidos");
                System.out.println("==================================================\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
    }

}
