import java.util.Scanner;

/*
 * Clase FR1_FR2
 * Programa que realiza las siguientes funcionalidades:
 * FR1: Lee caracteres desde la entrada estándar hasta encontrar un asterisco '*'.
 * FR2: Muestra por pantalla toda la información leída después de recibir el carácter de terminación.
 * Control de errores incluido con try/catch.
 */

public class FR1_FR2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder texto = new StringBuilder(); // Almacena los caracteres leídos

        System.out.println("Introduce texto. Finaliza con '*' :");

        try {
            char c;

            // ---------- FR1: Leer caracteres hasta '*' ----------
            while (true) {
                String entrada = scanner.nextLine();
                if (entrada.length() == 0) continue; // ignora líneas vacías

                for (int i = 0; i < entrada.length(); i++) {
                    c = entrada.charAt(i);
                    if (c == '*') { // carácter de terminación

                        // ---------- FR2: Mostrar la cadena leída ----------
                        System.out.println("Texto completo leído: " + texto.toString());
                        return;
                    }
                    texto.append(c);
                }
                texto.append("\n"); // mantener saltos de línea
            }
        } catch (Exception e) {
            System.out.println("Error al leer la entrada: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
