import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Hilo1 extends Thread {
    private String nombreArchivo;
    long t_comienzo, t_fin, t_total;

    public Hilo1(String nombreArchivo) throws IOException {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void run() {
        int contador = 0;
        FileReader fr;
        int caract;
        try {
            fr = new FileReader(nombreArchivo);
            caract = fr.read();
            while (caract != -1) {
                caract = fr.read();
                contador++;
            }
            t_comienzo, t_fin;
            t_comienzo = System.currentTimeMillis();
            t_fin = System.currentTimeMillis();
            t_total = t_fin - t_comienzo;
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Este es el hilo 1 " + " " + contador + " " + t_total);
    }
}

