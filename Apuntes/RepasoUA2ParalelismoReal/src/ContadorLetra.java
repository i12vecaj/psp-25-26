import java.io.BufferedReader;
import java.io.FileReader;

public class ContadorLetra implements Runnable {

    private final String fichero;

    private final char letras;

    private int contador;

    public ContadorLetra(String fichero, char letras) {
        this.fichero = fichero;
        this.letras = letras;
        this.contador = contador;
    }


    @Override
    public void run() {

        System.out.println(" Hilo inicia para buscar letras " + letras);
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {

            int letra;
            while ((letra = br.read()) != -1) {
                char caracter = (char) letra;


                if (Character.toLowerCase(caracter) == Character.toLowerCase(letras)) {
                    contador++;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Hilo de la " + letras + " ha encontrado " + contador);
    }

    public int getContador() {
        return contador;
    }


}
