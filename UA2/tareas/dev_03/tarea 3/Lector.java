package tarea3hilos;

import java.io.FileReader;
import java.io.IOException;
	
public class Lector extends Thread{
	public static void main(String[] args) throws InterruptedException {
	
		String nombreFichero1 = "fichero1.txt";
		String nombreFichero2 = "fichero2.txt";
        int contador = 0;

        try {
            FileReader fr = new FileReader(nombreFichero1);

            int c;
            while ((c = fr.read()) != -1) {
                contador++;
            }

            fr.close();

            System.out.println("El fichero 1 tiene: " + contador + " caracteres");

        } catch (IOException e) {
            System.out.println("No se pudo leer el fichero: " + nombreFichero1);
        }
        try {
            FileReader fr = new FileReader(nombreFichero2);

            int c;
            while ((c = fr.read()) != -1) {
                contador++;
            }

            fr.close();

            System.out.println("El fichero 2 tiene: " + contador + " caracteres");

        } catch (IOException e) {
            System.out.println("No se pudo leer el fichero: " + nombreFichero2);
        }
    }
}
