// Este archivo corresponde al apartado FR1 y FR2.
// El apartado FR3 se encuentra en el README.MD

import java.io.FileNotFoundException;
import java.io.FileReader;

// Clase que extiende Thread para leer un fichero y contar sus caracteres
public class hilolectura extends Thread {

    // Ruta del fichero a leer
    private final String fichero;

    // Nombre descriptivo del fichero (solo para mostrar en pantalla)
    private final String nombre;

    // Contador de caracteres leídos
    int contador = 0;

    // Constructor: recibe la ruta del fichero y un nombre identificativo
    public hilolectura(String fichero, String nombre){
        this.fichero = fichero;
        this.nombre = nombre;
    }

    // Método que ejecuta el hilo
    public void run(){
        FileReader fr = null;  // Lector del fichero
        long inicio = System.currentTimeMillis(); // Tiempo inicial para medir duración

        try {
            // Abrir el fichero
            fr = new FileReader(fichero);

            // Leer el primer carácter (FileReader.read() devuelve un int)
            int caract = fr.read();

            // Leer carácter a carácter hasta que read() devuelva -1 (EOF)
            while (caract != -1) {
                contador++;      // Incrementar el contador por cada carácter
                caract = fr.read();  // Leer el siguiente carácter
            }

            long fin = System.currentTimeMillis();  // Tiempo final

            // Mostrar resultados
            System.out.println("El libro " + nombre + " tiene en total: " + contador + " caracteres.");
            System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");

        } catch (FileNotFoundException e) {

            // El fichero no existe o la ruta es incorrecta
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());

        } catch (Exception e) {

            // Cualquier otro error de lectura
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());

        } finally {

            // Cerrar el fichero siempre que se haya abierto
            try {
                if (fr != null)
                    fr.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }
}
