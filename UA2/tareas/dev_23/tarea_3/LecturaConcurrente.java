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

/*
 * ==============================================================================
 * DOCUMENTACIÓN Y FR3: COMPARACIÓN DEL TIEMPO DE EJECUCIÓN (Comentario solicitado)
 * ==============================================================================
 * * 1. Funcionamiento del programa (FR2):
 * - La clase 'ProcesadorFichero' implementa 'Runnable' y contiene la lógica
 * de conteo.
 * - La clase 'LecturaConcurrente' detecta todos los archivos .txt.
 * - Por cada archivo, crea un nuevo objeto 'Thread' pasándole el 'ProcesadorFichero'
 * como tarea.
 * - Se llama a 'hilo.start()' para que cada tarea comience a ejecutarse en paralelo.
 * - Se utiliza el método 'hilo.join()' para que el hilo principal espere a que
 * TODOS los hilos de conteo terminen antes de detener el cronómetro y finalizar.
 * Esto asegura que la medición de tiempo (FR3) sea correcta.
 * * 2. Diferencias observadas (FR3: Comparación Secuencial vs. Concurrente):
 * * - TIEMPO SECUENCIAL (FR1): El tiempo total es la SUMA de los tiempos de lectura
 * de cada fichero (T1 + T2 + T3 + ...). Solo se usa un hilo (el hilo principal).
 * * - TIEMPO CONCURRENTE (FR2): El tiempo total es aproximadamente igual al tiempo
 * que tarda en procesarse el fichero más grande, más el overhead de la gestión
 * de hilos.
 * * - CONCLUSIÓN ESPERADA: Para archivos grandes (como El Quijote) y en sistemas
 * multinúcleo, la ejecución CONCURRENTE suele ser **mucho más rápida**. Esto
 * se debe a que el sistema operativo puede leer varios ficheros al mismo tiempo
 * (paralelismo) y aprovechar los tiempos muertos de espera del disco (E/S).
 * * FÓRMULA DE COMPARACIÓN (t_total del FR1 vs. t_total del FR2):
 * * t_total_FR3 = t_total_FR1 - t_total_FR2
 * * Si el resultado es POSITIVO (que es lo esperado), la ejecución CONCURRENTE es mejor.
 * * ==============================================================================
 */