import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContadorCaracteresHilo extends Thread {

    private String nombreFichero;

    public ContadorCaracteresHilo(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    @Override
    public void run() {
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            int c;
            while ((c = br.read()) != -1) {
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero: " + e.getMessage());
        }

        System.out.println("El fichero " + nombreFichero + " tiene " + contador + " caracteres.");
    }
}
