import java.io.*;

public class LeerFichero implements Runnable {
    String nombreFichero;
    long caracteres;

    public LeerFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        this.caracteres = 0;
    }

    public void run() {
        try (FileReader fr = new FileReader(nombreFichero)) {
            while (fr.read() != -1) {
                caracteres++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR Fichero no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        }
    }

    public long getCaracteres() {
        return caracteres;
    }
}
