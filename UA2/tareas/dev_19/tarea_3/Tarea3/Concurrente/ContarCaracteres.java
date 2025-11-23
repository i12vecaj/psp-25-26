package Tarea3.Concurrente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContarCaracteres {

    public static long contar(String fichero) {
        long contador = 0;


        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            int caracter;

            //Leemos cada carácter hasta llegar al final del fichero (-1)
            while ((caracter = br.read()) != -1) {
                contador++; // Incrementamos el contador por cada carácter leído.
            }

            // cualquier error de lectura
        } catch (IOException e) {
            System.err.println("Error al leer el fichero " + fichero + ": " + e.getMessage());
        }


        return contador;
    }
}
