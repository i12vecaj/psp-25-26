import java.util.Random;
import java.util.TreeMap;

public class ModuloTrabajo extends Thread{

    private String nombreModulo;

    public ModuloTrabajo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    @Override
    public String toString() {
        return "Módulotrabajo{ nombre=" + nombreModulo + ", id= "+Thread.currentThread().threadId()+
                ", prioridad="+Thread.currentThread().getPriority()+"}";
    }

    @Override
    public void run() {
        Random randomNumbers = new Random();
        System.out.println("Modulo "+nombreModulo+ " iniciado. Id: " + threadId());
        for (int i = 0; i < 5; i++) {
            System.out.println("Modulo "+nombreModulo+ " - iteración "+ i);
                try {
                    Thread.sleep(randomNumbers.nextInt(500) + 300);
                } catch (InterruptedException e) {
                    System.out.println("Modulo "+nombreModulo+ " interrumpido durante la ejecución");
                    break;
                }
                    if (i==3){
                        Thread.yield();}
        }
    }
}
