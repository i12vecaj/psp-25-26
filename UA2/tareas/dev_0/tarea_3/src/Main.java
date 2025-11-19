import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        long t_comienzo, t_fin, t_total;
        t_comienzo = System.currentTimeMillis();

        try {
            FileReader fr = new FileReader("src/El Quijote.txt");
            FileReader fr2 = new FileReader("src/Odisea.txt");
            FileReader fr3 = new FileReader("src/Crimen y castigo.txt");

            int caract, caract2, caract3;
            int contador1 = 0, contador2 = 0, contador3 = 0;

            // --- El Quijote ---
            while ((caract = fr.read()) != -1) {
                contador1++;
            }
            System.out.println("El Quijote tiene " + contador1 + " caracteres");

            // --- La Odisea ---
            while ((caract2 = fr2.read()) != -1) {
                contador2++;
            }
            System.out.println("La Odisea tiene " + contador2 + " caracteres");

            // --- Crimen y castigo ---
            while ((caract3 = fr3.read()) != -1) {
                contador3++;
            }
            System.out.println("Crimen y castigo tiene " + contador3 + " caracteres");

            fr.close();
            fr2.close();
            fr3.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        t_fin = System.currentTimeMillis();
        t_total = t_fin - t_comienzo;

        System.out.println("\nTiempo TOTAL del programa: " + t_total + " ms");
    }
}
