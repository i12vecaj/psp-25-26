import java.util.Random;

public class HiloCamarero implements Runnable {

    private final Barra barra; // Referencia al objeto compartido
    private final String nombre;
    private final Random random = new Random();

    public HiloCamarero(String nombre, Barra barra) {
        this.nombre = nombre;
        this.barra = barra;
    }

    @Override
    public void run() {
        int contadorCervezas = 1;

        while (true) { // Bucle infinito: Moe nunca descansa
            try {
                // 1. Simular tiempo de tirar la caña (entre 0.5 y 1 seg)
                Thread.sleep(random.nextInt(500) + 500);

                String cerveza = "Duff #" + contadorCervezas++;

                System.out.println(nombre + " está preparando " + cerveza + "...");

                // 2. Intentar ponerla en la barra (Aquí se puede bloquear si está llena)
                barra.ponerCerveza(cerveza);

            } catch (InterruptedException e) {
                System.out.println(nombre + " ha cerrado el bar (Interrumpido).");
                return; // Salimos del bucle
            }
        }
    }
}