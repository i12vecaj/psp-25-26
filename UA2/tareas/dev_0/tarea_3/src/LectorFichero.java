import java.io.FileReader;
import java.io.IOException;

class LectorFichero implements Runnable {

    private String ruta;

    public LectorFichero(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public void run() {
        try (FileReader fr = new FileReader(ruta)) {

            int caract;
            int contador = 0;

            while ((caract = fr.read()) != -1) {
                contador++;
            }

            System.out.println("Hilo [" + Thread.currentThread().getName() +
                    "] → " + ruta + " → Caracteres: " + contador);

        } catch (IOException e) {
            System.out.println("Error leyendo " + ruta + ": " + e.getMessage());
        }
    }
}


public void main(String[] args) {

    long t_comienzo, t_fin, t_total;
    t_comienzo = System.currentTimeMillis();

    Thread hilo1 = new Thread(new LectorFichero("src/El Quijote.txt"));
    Thread hilo2 = new Thread(new LectorFichero("src/Odisea.txt"));
    Thread hilo3 = new Thread(new LectorFichero("src/Crimen y castigo.txt"));

    hilo1.start();
    hilo2.start();
    hilo3.start();

    System.out.println("Hilos creados a mano lanzados.");

    try {
        hilo1.join();
        hilo2.join();
        hilo3.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    t_fin = System.currentTimeMillis(); 
    t_total = t_fin - t_comienzo;

    System.out.println("Tiempo TOTAL del programa: " + t_total + " ms");
}


