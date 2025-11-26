package tarea3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class ContadorHilo extends Thread {

    private String nombreFichero;
    private long caracteres = -1;

    public ContadorHilo(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public long getCaracteres() {
        return caracteres;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    @Override
    public void run() {
        long cuenta = 0;
        try (Scanner scanner = new Scanner(new File(nombreFichero))) {
            scanner.useDelimiter("");
            while (scanner.hasNext()) {
                scanner.next();
                cuenta++;
            }
            caracteres = cuenta;
        } catch (FileNotFoundException e) {
            System.err.println("Error en el hilo: El fichero no se encuentra o no se puede leer: " + nombreFichero);
            caracteres = -1;
        }
    }
}