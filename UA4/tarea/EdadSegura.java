import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EdadSegura {
    private static final int MIN_EDAD = 0;
    private static final int MAX_EDAD = 90;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.print("Introduce tu edad: ");
                String linea = reader.readLine();

                if (linea == null) {
                    System.out.println("Entrada finalizada.");
                    return;
                }

                linea = linea.trim();
                if (linea.isEmpty()) {
                    System.out.println("No has escrito nada. Intenta de nuevo.");
                    continue;
                }

                int edad;
                try {
                    edad = Integer.parseInt(linea);
                } catch (NumberFormatException ex) {
                    System.out.println("Eso no es un numero valido. Intenta de nuevo.");
                    continue;
                }

                if (edad < MIN_EDAD || edad > MAX_EDAD) {
                    System.out.println("Edad fuera de rango (" + MIN_EDAD + " a " + MAX_EDAD + ").");
                    continue;
                }

                System.out.println("Edad registrada: " + edad);
                return;
            } catch (IOException ex) {
                System.out.println("Error de lectura. Intenta de nuevo.");
            } catch (Exception ex) {
                System.out.println("Ocurrio un error inesperado. Intenta de nuevo.");
            }
        }
    }
}
