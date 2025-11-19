package U2.T3;

import java.io.FileReader;
import java.util.Scanner;

/* Aqui lo que hacemos es pregunatrle al usuario que introduzca el nombre del archivo u cremaos una hilo el cual cuando se ejecuta lo que hace
   es contar los caraccteres del arhcivo del hilo y luego vuelve a preguntar si quieres conatr otro archivo aproximadamente este programa tarda 83 ms */
public class Tarea3_conhilos implements Runnable {

    private String nombreArchivo;

    // Constructor recibe el nombre del archivo
    public Tarea3_conhilos(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void run() {
         long t_comienzo = System.currentTimeMillis();
        int caracteres = 0;

        try (FileReader fr = new FileReader(nombreArchivo)) {
            int caracter;
            while ((caracter = fr.read()) != -1) {
                caracteres++;
            }
            System.out.println("El archivo " + nombreArchivo + " tiene: " + caracteres + " caracteres");
        } catch (Exception e) {
            System.out.println("Error leyendo " + nombreArchivo + ": " + e.getMessage());
        }
         long t_fin = System.currentTimeMillis();  // <<< FIN DEL TIEMPO
    System.out.println("Tiempo de ejecución del hilo para " + nombreArchivo + ": " + (t_fin - t_comienzo) + " ms");
    }

    public static void main(String[] args) {
        System.out.println("Carpeta de ejecución: " + System.getProperty("user.dir"));
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Introduce el nombre del archivo que quieres leer (FIN para salir):");
            String nombreArchivo = sc.nextLine();

            if (nombreArchivo.equalsIgnoreCase("FIN")) {
                System.out.println("Fin del programa.");
                break;
            }

            // Crear hilo para procesar este archivo
            Thread hilo = new Thread(new Tarea3_conhilos(nombreArchivo));
            hilo.start();

            System.out.println(); // Línea en blanco para separar
        }
    }
}
