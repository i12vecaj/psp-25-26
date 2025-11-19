import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LecturaSecuencial {

    public static void main(String[] args) {

        System.out.println("=== PROCESAMIENTO SECUENCIAL ===");

        List<Path> listaFicheros = new ArrayList<>();
        Path ruta = Paths.get(".");

        // Búsqueda automática de archivos .txt
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(ruta)) {
            for (Path entry : stream) {
                // Filtramos archivos que terminen en .txt
                if (entry.toString().endsWith(".txt") && Files.isRegularFile(entry)) {
                    listaFicheros.add(entry);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al listar archivos: " + e.getMessage());
            return;
        }

        if (listaFicheros.isEmpty()) {
            System.out.println("No se encontraron archivos terminados en .txt");
            return;
        }

        System.out.println("Archivos detectados: " + listaFicheros.size() + "\n");

        // Inicio de la medición de tiempo
        long t_comienzo = System.currentTimeMillis();

        // Bucle Secuencial: Procesar uno por uno
        for (Path pathFichero : listaFicheros) {
            contarCaracteres(pathFichero);
        }

        // Fin de la medición de tiempo
        long t_fin = System.currentTimeMillis();
        long t_total = t_fin - t_comienzo; // FR3: Cálculo total

        System.out.println("\n=== FIN DEL PROCESO SECUENCIAL ===");
        System.out.println("Tiempo total de ejecución: " + t_total + " ms");
    }


    private static void contarCaracteres(Path pathFichero) {
        long contador = 0; // Inicializamos un contador

        try (BufferedReader lector = new BufferedReader(new FileReader(pathFichero.toFile()))) {
            int caracter;

            while((caracter = lector.read()) != -1) {
                contador++; //
            }

            // Imprimimos el resultado de este fichero
            System.out.println("Procesado: " + pathFichero.getFileName() +
                    " -> Caracteres totales: " + contador);

        } catch (IOException e) {
            // Manejo de errores de lectura/fichero no encontrado
            System.err.println("Error al procesar el fichero " + pathFichero.getFileName() + ": " + e.getMessage());
        }}
}