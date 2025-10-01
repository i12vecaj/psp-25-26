package tarea2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * LectorCadena
 * ------------
 * FR1: Lee caracteres desde la entrada estándar hasta encontrar '*'.
 * FR2: Muestra por pantalla toda la información leída.
 * Se implementa control de errores.
 */
public class LectorCadena {
    public static void main(String[] args) {
        StringBuilder buffer = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int caracter;
            System.out.println("Introduce texto (finaliza con '*'):");
            
            // Leer carácter a carácter
            while ((caracter = br.read()) != -1) {
                char c = (char) caracter;
                if (c == '*') {
                    break; // FR1: detenerse al encontrar '*'
                }
                buffer.append(c);
            }

            // FR2: Mostrar lo leído
            System.out.println("\n=== TEXTO INTRODUCIDO ===");
            System.out.println(buffer.toString());

        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}

