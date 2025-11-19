import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * Clase encargada de la lógica, Leer y contar caracteres.
 * Implementa Runnable para ser ejecutada por un hilo (FR2).
 */
public class ProcesadorFichero implements Runnable {

    private String nombreFichero;

    public ProcesadorFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    /*
     * Metodo principal que el hilo ejecuta al llamar a Thread.start().
     */
    @Override
    public void run() {
        contar();
    }

    /*
     * Metodo que realiza el conteo de caracteres.
     */
    public void contar() {
        long contador = 0;
        // Obtenemos el nombre del hilo actual
        String nombreHilo = Thread.currentThread().getName();

        try (BufferedReader lector = new BufferedReader(new FileReader(nombreFichero))) {
            while (lector.read() != -1) {
                contador++;
            }

            // Usamos 'synchronized' para asegurar que el mensaje completo de un hilo no se mezcle con el mensaje de otro hilo en la consola.
            synchronized (System.out) {
                System.out.println("Archivo: " + nombreFichero +
                        " -> Caracteres: " + contador +
                        " [Hilo: " + nombreHilo + "]");
            }

        } catch (IOException e) {
            System.err.println("Error leyendo '" + nombreFichero + "': " + e.getMessage());
        }
    }
}