import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// hio encargado de leer cada archivo y contar sus caracteres
public class LectorFile implements Runnable {

    private String fileName;

    public LectorFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        contar();
    }

    // Metodo que cuenta los caracteres de un archivo.
    public void contar() {
        long contador = 0;
        // Obtenemos el nombre del hilo actual
        String nombreHilo = Thread.currentThread().getName();

        try (BufferedReader lector = new BufferedReader(new FileReader(fileName))) {
            while (lector.read() != -1) {
                contador++;
            }

            // synchronized para evitar conflictos de acceso a los archivos 
            synchronized (System.out) {
                System.out.println("Archivo: " + fileName +
                        " -> Caracteres: " + contador +
                        " [Hilo: " + nombreHilo + "]");
            }

        } catch (IOException e) {
            System.err.println("Error leyendo '" + fileName + "': " + e.getMessage());
        }
    }
}