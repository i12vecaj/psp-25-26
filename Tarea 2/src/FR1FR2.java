import java.util.Scanner;

public class FR1FR2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder textoLeido = new StringBuilder();

        System.out.println("Introduce caracteres. Para terminar, escribe '*'");

        try {
            while (true) {
                // Leemos línea por línea
                String linea = scanner.nextLine();

                for (char c : linea.toCharArray()) {
                    if (c == '*') {
                        // Si encontramos '*', terminamos la lectura
                        System.out.println("\nLectura terminada.");
                        System.out.println("Texto leído:");
                        System.out.println(textoLeido.toString());
                        return; // salimos del programa
                    }
                    textoLeido.append(c);
                }
                textoLeido.append("\n"); // para mantener saltos de línea
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al leer la entrada: " + e.getMessage());
        } finally {

            scanner.close();
        }
    }
}
