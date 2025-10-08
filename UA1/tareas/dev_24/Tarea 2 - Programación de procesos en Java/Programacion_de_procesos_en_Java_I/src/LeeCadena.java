import java.io.*;

//Programa que lee una cadena por teclado hasta recibir el carácter '*'

public class LeeCadena {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder texto = new StringBuilder();
            String linea;
            System.out.println("Introduce texto (termina con *):");

            //Leer caracteres hasta '*'
            while ((linea = br.readLine()) != null) {
                if (linea.contains("*")) {
                    texto.append(linea.substring(0, linea.indexOf('*')));
                    break;
                } else {
                    texto.append(linea).append("\n");
                }
            }

            //Mostrar la información leída
            System.out.println("\nContenido introducido:");
            System.out.println(texto.toString());

        } catch (IOException e) {
            System.err.println("Error al leer datos: " + e.getMessage());
        }
    }
}
