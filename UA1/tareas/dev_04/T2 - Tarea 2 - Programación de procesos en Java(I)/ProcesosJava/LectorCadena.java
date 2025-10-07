package ProcesosJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//1: Lee una cadena de caracteres hasta recibir un '*.
//2: Muestra por pantalla toda la información leída.
public class LectorCadena {
    public static void main(String[] args) {
        StringBuilder texto = new StringBuilder(); // Para acumular el texto
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduce texto (finaliza con '*'): ");

        try {
            int caracter;
            while ((caracter = reader.read()) != -1) { // Lee carácter a carácter
                char c = (char) caracter;
                if (c == '*') { // 1: carácter de terminación
                    break;
                }
                texto.append(c);
            }

            // 2: Mostrar la información leída
            System.out.println("\n--- TEXTO INTRODUCIDO ---");
            System.out.println(texto.toString());

        } catch (IOException e) {
            System.err.println("❌ Error al leer la entrada: " + e.getMessage());
        }
    }
}
