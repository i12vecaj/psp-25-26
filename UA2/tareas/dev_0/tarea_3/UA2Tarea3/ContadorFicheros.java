import java.io.File;
import java.io.FileReader;

public class ContadorFicheros {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            FileReader fr;
            FileReader fr2;
            FileReader fr3;
            FileReader fr4;
            int caract;
            int contador = -1;
            int contador2 = -1;
            int contador3 = -1;
            int contador4 = -1;
            fr = new FileReader("El Quijote.txt");
            fr2 = new FileReader("Cancion Pokemon.txt");
            fr3 = new FileReader("Odisea.txt");
            fr4 = new FileReader("Crimen y castigo.txt");

            caract = fr.read();
            while (caract != -1) {

                caract = fr.read();
                contador++;
            }
            fr.close();
            caract = fr2.read();
            while (caract != -1) {

                caract = fr2.read();
                contador2++;
            }
            fr2.close();
            caract = fr3.read();
            while (caract != -1) {

                caract = fr3.read();
                contador3++;
            }
            fr3.close();
            caract = fr4.read();
            while (caract != -1) {

                caract = fr4.read();
                contador4++;
            }
            fr4.close();
            System.out.println("El fichero El Quijote.txt tiene " + contador + " caracteres.");
            System.out.println("El fichero Cancion Pokemon.txt tiene " + contador2 + " caracteres.");
            System.out.println("El fichero Odisea.txt tiene " + contador3 + " caracteres.");
            System.out.println("El fichero Crimen y castigo.txt tiene " + contador4 + " caracteres.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tiempo total de ejecución: " + totalTime + " ms");

    }

}