package Parte2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManejadorArchivo implements Runnable {

    private final String rutaDocumento;

    public ManejadorArchivo(String rutaDocumento) {
        this.rutaDocumento = rutaDocumento;
    }

    @Override
    public void run() {
        ejecutarConteo();
    }

    public void ejecutarConteo() {
        long totalCaracteres = 0;
        String identificadorHilo = Thread.currentThread().getName();

        try (BufferedReader buffer = new BufferedReader(new FileReader(rutaDocumento))) {
            int byteLeido;
            // Estructura de lectura alternativa utilizando asignación interna estándar
            while ((byteLeido = buffer.read()) != -1) {
                totalCaracteres++;
            }

            // Sincronización sobre la consola para prevenir solapamientos de salida
            synchronized (System.out) {
                System.out.println("Archivo: " + rutaDocumento +
                        " -> Caracteres: " + totalCaracteres +
                        " [Hilo: " + identificadorHilo + "]");
            }

        } catch (IOException excepcionIO) {
            System.err.println("Error leyendo '" + rutaDocumento + "': " + excepcionIO.getMessage());
        }
    }
}