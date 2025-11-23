package Tarea3.Secuencial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContarCaracteres {

    //Metodo estático que recibe la ruta de un fichero y devuelve el número de caracteres que contiene
    public static long contar(String fichero) {
        long contador = 0; //se inicializa el contador de caracteres

        //se usa BufferedReader para leer el fichero carácter por carácter.
        //Esto permite contar con precisión todos los caracteres, incluyendo espacios y saltos de línea.
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            int caracter;

            //se lee cada carácter hasta llegar al final del fichero (-1).
            while ((caracter = br.read()) != -1) {
                contador++; // Incrementamos el contador por cada carácter leído.
            }

            //se captura cualquier error de lectura, como fichero no encontrado
        } catch (IOException e) {
            System.err.println("Error al leer el fichero " + fichero + ": " + e.getMessage());
        }

        //se devuelve el número total de caracteres contados
        return contador;
    }
}
