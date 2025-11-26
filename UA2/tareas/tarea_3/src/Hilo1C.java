import java.io.FileReader;
import java.io.IOException;

public class Hilo1C implements Runnable {

    private String nombreArchivo;

    public Hilo1C(String nombreArchivo) throws IOException {
        this.nombreArchivo = nombreArchivo;
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Este es el hilo  "   + " " + contador);
    }


}

