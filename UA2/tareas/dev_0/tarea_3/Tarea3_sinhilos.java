package U2.T3;

import java.io.FileReader;
import java.util.Scanner;
/* Aqui lo que hacemos es preguntarle al usuario que introduzca el nombre del arhcivo y le contamos los caracteres 
y cuando termine de contarlo vuelve a preguntar si quiere contar otro archivo  Este programa tarda aproximadamente 67ms*/
public class Tarea3_sinhilos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("Introduce el nombre del archivo que quieres leer (FIN para salir):");
            String nombre = sc.nextLine();

            if (nombre.equalsIgnoreCase("FIN")) {
                System.out.println("Fin del programa.");
                break;
            }

             long t_comienzo = System.currentTimeMillis(); // <<< INICIO DEL TIEMPO
            int caracteres = 0;

            try (FileReader fr = new FileReader(nombre)) {
                int caracter;
                while ((caracter = fr.read()) != -1) {
                    caracteres++;
                }
                System.out.println("El archivo " + nombre + " tiene: " + caracteres + " caracteres");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
             long t_fin = System.currentTimeMillis(); // <<< FIN DEL TIEMPO
    System.out.println("Tiempo de ejecución del archivo " + nombre + ": " + (t_fin - t_comienzo) + " ms");

            System.out.println(); // Línea en blanco para separar
        }
    }
}
