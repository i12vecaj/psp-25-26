import java.util.Scanner;

public class CapturadorTexto {

    public static void main(String[] args) {
        Scanner entradaTeclado = new Scanner(System.in);
        StringBuilder acumuladorLineas = new StringBuilder();
        System.out.println("Introduce texto. Escribe '*' para finalizar:");

        try {
            // Estructura de lectura alternativa controlando el flujo en la asignación continua
            String registroLinea;
            while (entradaTeclado.hasNextLine()) {
                registroLinea = entradaTeclado.nextLine();

                // Interrupción del bucle si detectamos el carácter de corte
                if (registroLinea.contains("*")) {
                    int posicionCorte = registroLinea.indexOf('*');
                    acumuladorLineas.append(registroLinea, 0, posicionCorte);
                    break;
                }

                acumuladorLineas.append(registroLinea).append(System.lineSeparator());
            }

            System.out.println("=== Contenido introducido ===");
            System.out.println(acumuladorLineas.toString());

        } catch (Exception exLectura) {
            System.err.println(" Error durante la lectura de datos: " + exLectura.getMessage());
        } finally {
            entradaTeclado.close();
        }
    }
}