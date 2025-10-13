import java.util.Scanner;

public class tarea3 {
    public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

        // Si no hay ningún dato disponible, salimos con código 1
        if (!sc.hasNext()) {
            System.exit(1);
        }

        String input = sc.next();

        try {
            // Intentamos convertir a número entero
            int numero = Integer.parseInt(input);

            // Si el número es menor que 0 → código 3
            if (numero < 0) {
                System.exit(3);
            } else {
                // En cualquier otro caso → código 0
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            // Si no es un número → código -1
            System.exit(-1);
        }

    }
}
