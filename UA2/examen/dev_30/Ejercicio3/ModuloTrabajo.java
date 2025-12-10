import java.util.Random;

public class ModuloTrabajo extends Thread{
    private static final Random RANDOM = new Random();
    private String nombre;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public void run() {
        System.out.println("Modulo " + nombre + "Iniciado. ID: " + Thread.currentThread().getId());
        for (int i = 0; i <= 5; i++) {
            if (i == 3){
                Thread.yield();
            }
            try {
                int waitingTime = RANDOM.nextInt(800)+300;
                System.out.println("Modulo " + nombre + " Suspendido Durante: " + waitingTime + "ms");
                Thread.sleep(waitingTime);
                System.out.println("Modulo " + nombre + " - Iteración " + i);
            }catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName()+", Interrumpido Durante la Ejecución");
                Thread.currentThread().interrupt();
            }
        }
    }
    @Override public String toString() {
    return Thread.currentThread().toString();
}}