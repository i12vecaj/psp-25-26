/**
 ** Autor: Jose Antonio Roda Donoso
 *  * Curso: 2º DAM
 *  * Unidad: UA2 - Tarea 3 ContadorFichero
 *
 * Contiene un método estático para contar caracteres de un fichero.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContadorFichero {

    public static int contarCaracteres(String nombreFichero) throws IOException {
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            int c;
            while ((c = br.read()) != -1) {
                contador++;
            }
        }

        return contador;
    }
}
