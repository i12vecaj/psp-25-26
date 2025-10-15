/**

 * Este programa ejecuta el programa Main y se le pasa un argumento, en Main hay
 * varios condicionales que dependiendo el argumento introducido hacen que el programa devuelva
 * un código de salida u otro. Después de recibir el código de salidad de Main, el programa imprimirá
 * un mensaje u otro dependiendo del código recibido
 */

import java.io.IOException;
import java.util.Scanner;

public class Lanzador {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Introduce un argumento para pasar a Main: ");
            String argumento = scanner.nextLine();

            // Obtener el mismo classpath que usa este programa (automático)
            String classpath = System.getProperty("java.class.path");

            // Ejecutar Main con el argumento introducido
            Process proceso = new ProcessBuilder("java", "-cp", classpath, "Main", argumento).start();
            int codigoSalida = proceso.waitFor();

            // Mostrar el resultado según el código devuelto por Main
            switch (codigoSalida) {
                case 1:
                    System.out.println("No se introdujo ningún argumento.");
                    break;
                case 2:
                    System.out.println("El argumento es una cadena (no es un número entero).");
                    break;
                case 3:
                    System.out.println("El número es menor que 0.");
                    break;
                case 0:
                    System.out.println("El número es válido (entero >= 0).");
                    break;
                default:
                    System.out.println("Código de salida desconocido: " + codigoSalida);
            }

        } catch (IOException e) {
            System.out.println("Error al ejecutar Main: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("La ejecución fue interrumpida.");
        } finally {
            scanner.close();
        }
    }
}
