import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContadorCaracteresHilo implements Runnable {

    private final String nombreFichero;

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
            System.out.println("Error leyendo " + nombreFichero + ": " + e.getMessage());
        }

        long idHilo = Thread.currentThread().getId();
        System.out.println("[" + nombreFichero + "] → " + contador + " caracteres (hilo " + idHilo + ")");
    }
}
