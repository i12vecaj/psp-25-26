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
        System.out.println("segundo apartado con hilos");

        // filtrado de archivos .txt en la carpeta del proyecto
        List<String> fileList = new ArrayList<>();
        Path ruta = Paths.get(".");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(ruta)) {
            for (Path entry : stream) {
                // Filtramos archivos que terminen en .txt
                if (entry.toString().endsWith(".txt") && Files.isRegularFile(entry)) {
                    // metemos en el array el nombre del archivo
                    fileList.add(entry.getFileName().toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al listar archivos: " + e.getMessage());
            return;
        }

        if (fileList.isEmpty()) {
            System.err.println("No se encontraron archivos .txt en la carpeta del proyecto.");
            return;
        }
        System.out.println("Archivos detectados: " + fileList.size());

        // Inicio de la medición de tiempo
        long medicionInicial = System.currentTimeMillis();

        // EJECUCIÓN CONCURRENTE: Crear y lanzar un hilo por cada fichero
        List<Thread> hilos = new ArrayList<>();

        for (String nombreFichero : fileList) {
            // Creamos una instancia de la clase Runnable
            LectorFile procesador = new LectorFile(nombreFichero);

            // Creamos el hilo y le pasamos la tarea (el objeto procesador)
            Thread hilo = new Thread(procesador, "Hilo-" + nombreFichero);
            hilos.add(hilo);

            // El hilo comienza su ejecución en paralelo
            hilo.start();
        }

        for (Thread h : hilos) {
            try {
                // join() obliga al programa principal a esperar a que este hilo termine
                // En este caso, esperamos a que cada hilo termine antes de continuar
                h.join();
            } catch (InterruptedException e) {
                System.err.println("El hilo principal fue interrumpido mientras esperaba.");
            }
        }

        long medicion = System.currentTimeMillis();// medicion final
        long tiempo_T = medicion - medicionInicial;

        System.out.println("lectura finalizada");
        System.out.println("tiempo usando hilos: " + tiempo_T + " ms");
    }
}