import java.io.FileReader;
import java.util.jar.Manifest;

public class ContadorFicherosHilos implements Runnable {
    private String nombreFichero;
    private int contador;

    public ContadorFicherosHilos(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        this.contador = 0;
    }

    @Override
    public void run() {
        try {
            FileReader fr;
            int caract;
            fr = new FileReader(nombreFichero);
            caract = fr.read();
            while (caract != -1) {
                caract = fr.read();
                contador++;
            }
            fr.close();
            System.out.println("El fichero " + nombreFichero + " tiene " + contador + " caracteres.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Thread hilo1 = new Thread(new ContadorFicherosHilos("El Quijote.txt"));
        Thread hilo2 = new Thread(new ContadorFicherosHilos("Cancion Pokemon.txt"));
        Thread hilo3 = new Thread(new ContadorFicherosHilos("Odisea.txt"));
        Thread hilo4 = new Thread(new ContadorFicherosHilos("Crimen y castigo.txt"));

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tiempo total de ejecución: " + totalTime + " ms");
    }

}
