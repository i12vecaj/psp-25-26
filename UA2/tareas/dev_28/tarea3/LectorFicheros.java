import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
// Clase lectora de ficheros
public class LectorFicheros implements Runnable{

    private String fichero;

    public LectorFicheros(String fichero) {
        this.fichero = fichero;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    //funcion que se encarga de leer el fichero
    public int leerFichero() throws IOException {
        int numCaracteres = 0;

        FileReader fr;
        int caract;
        fr = new FileReader(this.fichero);
        caract = fr.read();
        while(caract != -1) {
            numCaracteres++;
            caract = fr.read();
        }

        return numCaracteres;

    }

    // La funcion run de la interfaz
    @Override
    public void run() {
        System.out.println("Comenzando a leer fichero");
        try {
            System.out.println("Total de caracteres en el fichero " + this.fichero + " : " + leerFichero());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
