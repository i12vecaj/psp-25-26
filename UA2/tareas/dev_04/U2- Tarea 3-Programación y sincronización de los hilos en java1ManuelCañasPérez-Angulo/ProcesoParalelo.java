package Parte2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProcesoParalelo {

    public static void main(String[] args) {
        System.out.println("segundo apartado con hilos");

        List<String> listadoFicheros = new ArrayList<>();
        Path directorioActual = Paths.get(".");

        // Localización de ficheros de texto plano en el directorio raíz
        try (DirectoryStream<Path> flujoDirectorio = Files.newDirectoryStream(directorioActual)) {
            for (Path elemento : flujoDirectorio) {
                if (elemento.toString().endsWith(".txt") && Files.isRegularFile(elemento)) {
                    listadoFicheros.add(elemento.getFileName().toString());
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al listar archivos: " + ex.getMessage());
            return;
        }

        if (listadoFicheros.isEmpty()) {
            System.err.println("No se encontraron archivos .txt en la carpeta del proyecto.");
            return;
        }
        System.out.println("Archivos detectados: " + listadoFicheros.size());

        // Captura del tiempo inicial de ejecución
        long marcaTiempoInicio = System.currentTimeMillis();

        List<Thread> grupoHilos = new ArrayList<>();

        // Despliegue concurrente creando un hilo dedicado para cada archivo del contenedor
        for (String itemFichero : listadoFicheros) {
            ManejadorArchivo tarea = new ManejadorArchivo(itemFichero);
            Thread worker = new Thread(tarea, "Worker-" + itemFichero);
            grupoHilos.add(worker);
            worker.start();
        }

        // Bloqueo del hilo principal hasta que cada hilo secundario culmine
        for (Thread workerActivo : grupoHilos) {
            try {
                workerActivo.join();
            } catch (InterruptedException exInt) {
                System.err.println("El hilo principal fue interrumpido mientras esperaba.");
            }
        }

        long marcaTiempoFin = System.currentTimeMillis();
        long duracionTotal = marcaTiempoFin - marcaTiempoInicio;

        System.out.println("lectura finalizada");
        System.out.println("tiempo usando hilos: " + duracionTotal + " ms");
    }
}