package UA1.tareas.dev_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Tarea2 {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder cadenaLeida = new StringBuilder();

        System.out.println("=== LECTOR DE CADENAS ===");
        System.out.println("Ingrese caracteres (presione Enter después de cada línea)");
        System.out.println("Ingrese '*' en una linea para finalizar:");

        try {
            String linea;
            boolean terminado = false;

            // Leer líneas hasta encontrar el carácter de terminación
            while (!terminado && (linea = reader.readLine()) != null) {
                // Verificar si la línea contiene el carácter de terminación
                if (linea.contains("*")) {
                    // Encontrar la posición del asterisco
                    int posicionAsterisco = linea.indexOf('*');

                    // Agregar solo el texto antes del asterisco (si existe)
                    if (posicionAsterisco > 0) {
                        cadenaLeida.append(linea.substring(0, posicionAsterisco));
                    }

                    // Marcar como terminado
                    terminado = true;
                } else {
                    // Agregar la línea completa a la cadena acumulada
                    cadenaLeida.append(linea).append("\n");
                }
            }

            // FR2: Mostrar toda la información leída (solo después de terminar)
            System.out.println("\n=== INFORMACION LEIDA ===");
            if (cadenaLeida.length() > 0) {
                System.out.println(cadenaLeida.toString());
            } else {
                System.out.println("(No se ingreso texto)");
            }
            System.out.println("=== FIN DE LA INFORMACION ===");

        } catch (IOException e) {
            // FR4: Control de errores - Manejo de excepciones de E/S
            System.err.println("Error de entrada/salida: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            // FR4: Control de errores - Manejo de excepciones generales
            System.err.println("Error inesperado: " + e.getMessage());
            System.exit(1);
        } finally {
            // Cerrar recursos en el bloque finally para garantizar su liberación
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar el reader: " + e.getMessage());
            }
        }
    }
}