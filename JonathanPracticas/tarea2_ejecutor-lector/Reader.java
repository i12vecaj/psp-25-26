import java.io.*;

public class Reader {

    public static void main(String[] args) {
        BufferedReader lector = null;
        StringBuilder contenido = new StringBuilder();

        try {
            lector = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Escribe cosas (sal escribiendo *):");

            int caracterLeido;
            boolean finalizarProceso = false;

            // Leer caracteres
            while (!finalizarProceso && (caracterLeido = lector.read()) != -1) {
                char charActual = (char) caracterLeido;

                if (charActual == '*') {
                    finalizarProceso = true;
                } else {
                    contenido.append(charActual); // Unir caracteres
                }
            }

            // Revisar si hay contenido
            if (contenido.length() > 0) {
                System.out.println("--- Texto capturado ---");
                System.out.println(contenido.toString());
            } else {
                System.out.println("No se capturó nada.");
            }

        } catch (IOException excepcion) {
            System.err.println("Error IO: " + excepcion.getMessage());
            excepcion.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}