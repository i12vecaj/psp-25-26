import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lector {
    public static void main(String[] args) {
        StringBuilder texto = new StringBuilder(); //Guardar el texto puesto en la consola
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Leer de la consola

        System.out.println("Introduce texto (finaliza con *):");

        try {
            int caracter;
            //Leer hasta encontrar * y darle a enter
            while ((caracter = br.read()) != -1) {
                if ((char) caracter == '*') {
                    break; // termina al encontrar el asterisco
                }
                texto.append((char) caracter); //Añadir el caracter leido al texto
            }

            //Mostrar todo el texto leído
            System.out.println("---Contenido leído---");
            System.out.println(texto.toString());

            //Control de errores en la entrada y la salida
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage()); 
        }
    }
}