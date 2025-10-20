import java.io.IOException;

// Programa que lee caracteres desde la entrada estándar hasta encontrar '*'
// y luego muestra toda la información leída
public class LectorCaracteres {

    public static void main(String[] args) {
        StringBuilder contenido = new StringBuilder();
        int caracterLeido;
        boolean terminacionEncontrada = false;

        System.out.println("=== LECTOR DE CARACTERES ===");
        System.out.println("Introduzca texto. Finalice con el caracter '*'");
        System.out.println("-------------------------------------------");

        try {
            // FR1: Leer caracteres hasta encontrar el asterisco
            while ((caracterLeido = System.in.read()) != -1) {
                char c = (char) caracterLeido;

                // Verificar si es el carácter de terminación
                if (c == '*') {
                    terminacionEncontrada = true;
                    break;
                }

                // Agregar el carácter leído al contenido
                contenido.append(c);
            }

            // FR2: Mostrar toda la información leída
            System.out.println("\n=== CONTENIDO LEÍDO ===");

            if (terminacionEncontrada) {
                if (contenido.length() > 0) {
                    System.out.println(contenido.toString());
                    System.out.println("\n--- Fin del contenido ---");
                    System.out.println("Total de caracteres leídos: " + contenido.length());
                } else {
                    System.out.println("(No se introdujo ningún contenido antes del asterisco)");
                }
            } else {
                System.out.println("ADVERTENCIA: No se encontró el carácter de terminación '*'");
                if (contenido.length() > 0) {
                    System.out.println("Contenido leído hasta el final de la entrada:");
                    System.out.println(contenido.toString());
                }
            }

        } catch (IOException e) {
            // Control de errores: Manejo de excepciones de entrada/salida
            System.err.println("ERROR: Se produjo un error al leer la entrada.");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);

        } catch (Exception e) {
            // Control de errores: Manejo de excepciones generales
            System.err.println("ERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }

        System.out.println("\nPrograma finalizado correctamente.");
    }
}