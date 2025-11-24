package tarea3hilos;

import java.io.FileReader;
import java.io.IOException;

public class LectorFR2 extends Thread {
	 
	private String nombreFichero;

    public LectorFR2(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    @Override
    public void run() {
        int contador = 0;

        try {
            FileReader fr = new FileReader(nombreFichero);
            int c;
            while ((c = fr.read()) != -1) {
                contador++;
            }
            fr.close();

            System.out.println("El" + nombreFichero + "tiene: " + contador + " caracteres");

        } catch (IOException e) {
            System.out.println("No se pudo leer el fichero: " + nombreFichero);
        }
    }
}
/* FR3: Este programa es más rápido, aunque con mis archivos no se termina de apreciar porque no tienen demasiados caracteres, sé que es más rápido ya que usa programación concurrente*/
	

