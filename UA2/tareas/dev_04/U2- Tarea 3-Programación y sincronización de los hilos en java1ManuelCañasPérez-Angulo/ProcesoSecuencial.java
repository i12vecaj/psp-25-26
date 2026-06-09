package Parte2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProcesoSecuencial {

    public static void main(String[] args) {
        System.out.println("lectura secuencial sin hilos");

        List<Path> coleccionRutas = new ArrayList<>();
        Path carpetaRaiz = Paths.get(".");

        // Escaneo del directorio actual buscando coincidencias .txt
        try (DirectoryStream<Path> streamFicheros = Files.newDirectoryStream(carpetaRaiz)) {
            for (Path registro : streamFicheros) {
                if (registro.toString().endsWith(".txt") && Files.isRegularFile(registro)) {
                    coleccionRutas.add(registro);
                }
            }
        } catch (IOException err) {
            System.err.println("Error al listar archivos: " + err.getMessage());
            return;
        }

        if (coleccionRutas.isEmpty()) {
            System.out.println("No se encontraron archivos terminados en .txt");
            return;
        }

        System.out.println("Archivos detectados: " + coleccionRutas.size() + "\n");

        long cronometroInicio = System.currentTimeMillis();

        // Procesamiento en cascada uno tras otro sobre el mismo hilo de ejecución
        for (Path rutaArchivo : coleccionRutas) {
            analizarYContar(rutaArchivo);
        }

        long cronometroFin = System.currentTimeMillis();
        long lapsoTotal = cronometroFin - cronometroInicio;

        System.out.println("\n=== FIN DEL PROCESO SECUENCIAL ===");
        System.out.println("Tiempo total de ejecución: " + lapsoTotal + " ms");
    }

    private static void analizarYContar(Path pathFichero) {
        long acumuladorCaracteres = 0;

        try (BufferedReader lectorLineas = new BufferedReader(new FileReader(pathFichero.toFile()))) {
            // Estructura de lectura usando un bucle infinito controlado por break interno
            while (true) {
                int valorChar = lectorLineas.read();
                if (valorChar == -1) {
                    break;
                }
                acumuladorCaracteres++;
            }

            System.out.println("Procesado: " + pathFichero.getFileName() +
                    " -> Caracteres totales: " + acumuladorCaracteres);

        } catch (IOException errorAcceso) {
            System.err.println("Error al procesar el fichero " + pathFichero.getFileName() + ": " + errorAcceso.getMessage());
        }
    }
}