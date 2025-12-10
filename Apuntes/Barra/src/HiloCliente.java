import java.util.Random;

public class HiloCliente implements Runnable {

    private final Barra barra; // ¡Tiene que ser la MISMA barra que la del camarero!
    private final String nombre;
    private final Random random = new Random();

    public HiloCliente(String nombre, Barra barra) {
        this.nombre = nombre;
        this.barra = barra;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. Intentar coger una cerveza (Se bloquea si está vacía)
                String bebida = barra.beberCervezas(nombre);

                // 2. Bebérsela (simulamos que tarda un poco)
                System.out.println(nombre + " está disfrutando su " + bebida);
                Thread.sleep(random.nextInt(1000) + 1000);

            } catch (InterruptedException e) {
                System.out.println(nombre + " se va a casa (Interrumpido).");
                return;
            }
        }
    }
}