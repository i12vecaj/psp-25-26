import java.io.IOException;

public class LectorCaracteres {

    public static void main(String[] args) {
        StringBuilder buffer = new StringBuilder();
        int c;
        boolean fin = false;

        System.out.println("=== LECTOR DE CARACTERES ===");
        System.out.println("Escriba texto y finalice con '*'");
        System.out.println("--------------------------------");

        try {
            while ((c = System.in.read()) != -1) {
                char ch = (char) c;
                if (ch == '*') {
                    fin = true;
                    break;
                }
                buffer.append(ch);
            }

            System.out.println("\n=== CONTENIDO LEÍDO ===");
            if (fin) {
                if (buffer.length() > 0) {
                    System.out.println(buffer);
                    System.out.println("\n--- Fin del contenido ---");
                    System.out.println("Total de caracteres: " + buffer.length());
                } else {
                    System.out.println("(No se ingresó texto antes de '*')");
                }
            } else {
                System.out.println("No se encontró el carácter '*'.");
                if (buffer.length() > 0) {
                    System.out.println("Contenido parcial:");
                    System.out.println(buffer);
                }
            }

        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            System.exit(2);
        }

        System.out.println("\nPrograma finalizado.");
    }
}
