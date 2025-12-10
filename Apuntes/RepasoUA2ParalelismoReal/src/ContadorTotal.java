import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContadorTotal implements Runnable {

    private final String fichero;
    private int contador;

    public ContadorTotal(String fichero) {
        this.fichero = fichero;
        this.contador = 0;
    }

    @Override
    public void run() {
        System.out.println("-> Iniciando conteo total de: " + fichero);

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            // Leemos carácter a carácter hasta el final (-1)
            while (br.read() != -1) {
                contador++; // Sin preguntas. Si leemos algo, contamos.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("<- Fin conteo " + fichero + ". Total caracteres: " + contador);
    }

    public int getContador() {
        return contador;
    }
}