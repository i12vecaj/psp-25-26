import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LecturaConcurrente {

    public static void main(String[] args) {
        System.out.println("=== FR2: EJECUCIÓN CONCURRENTE (HILOS) ===\n");

        // Búsqueda automática de archivos .txt
        List<String> listaFicheros = new ArrayList<>();
        Path ruta = Paths.get(".");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(ruta)) {
            for (Path entry : stream) {
                // Filtramos archivos que terminen en .txt
                if (entry.toString().endsWith(".txt") && Files.isRegularFile(entry)) {
                    // Agregamos solo el nombre del archivo para que funcione correctamente
                    listaFicheros.add(entry.getFileName().toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al listar archivos: " + e.getMessage());
            return;
        }

        if (listaFicheros.isEmpty()) {
            System.err.println("No se encontraron archivos .txt en la carpeta del proyecto.");
            return;
        }

        System.out.println("Archivos detectados: " + listaFicheros.size());

        // Inicio de la medición de tiempo
        long t_comienzo = System.currentTimeMillis();

        // EJECUCIÓN CONCURRENTE: Crear y lanzar un hilo por cada fichero
        List<Thread> hilos = new ArrayList<>();

        for (String nombreFichero : listaFicheros) {
            // Creamos una instancia de la clase Runnable
            ProcesadorFichero procesador = new ProcesadorFichero(nombreFichero);

            // Creamos el hilo y le pasamos la tarea (el objeto procesador)
            Thread hilo = new Thread(procesador, "Hilo-" + nombreFichero);
            hilos.add(hilo);

            // El hilo comienza su ejecución en paralelo
            hilo.start();
        }

        // 4. Esperar a que todos los hilos terminen (Sincronización)
        for (Thread h : hilos) {
            try {
                // join() obliga al programa principal a esperar a que este hilo termine
                h.join();
            } catch (InterruptedException e) {
                System.err.println("El hilo principal fue interrumpido mientras esperaba.");
            }
        }

        // 5. Fin de la medición de tiempo (FR3)
        long t_fin = System.currentTimeMillis();
        long t_total = t_fin - t_comienzo;

        System.out.println("\n=== PROCESO TERMINADO ===");
        System.out.println("Tiempo total (Concurrente): " + t_total + " ms");
    }
}