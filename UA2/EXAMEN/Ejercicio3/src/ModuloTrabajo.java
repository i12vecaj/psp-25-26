import java.util.Random;

public class ModuloTrabajo extends Thread {
    private String nombre;
    private Random random;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
        this.random = new Random();
    }
//get id esta deprecated pero lo uso por claridad de codigo y costumbre
    @Override
    public void run() {
        System.out.println(nombre + " iniciado. ID: " + Thread.currentThread().getId());

        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(nombre + " - iteración " + i);
                
                if (i == 3) {
                    Thread.yield();
                }

                int sleepTime = 300 + random.nextInt(501);
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {

            System.out.println(nombre + " interrumpido durante la ejecución");
            return; 
        }
    }

    @Override
    public String toString() {
        return "MóduloTrabajo{nombre='" + nombre + "', id=" + this.getId() + ", prioridad=" + this.getPriority() + "}";
    }
}
