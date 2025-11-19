// Este archivo corresponde al apartado FR1 y FR2.
// El apartado FR3 se encuentra en el README.MD

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {

        // Rutas absolutas de los dos ficheros a leer (FR2)
        String Fichero1 = "C:\\Users\\nieto\\Documentos\\IntelliJ\\tarea_3\\src\\la_metamorfosis.txt";
        String Fichero2 = "C:\\Users\\nieto\\Documentos\\IntelliJ\\tarea_3\\src\\el_quijote.txt";

        // Crear dos hilos, cada uno asociado a un fichero
        // El segundo argumento es solo un nombre descriptivo para imprimir
        hilolectura f1 = new hilolectura(Fichero1, "La Metamorfósis");
        hilolectura f2 = new hilolectura(Fichero2, "El Quijote");

        // Iniciar los hilos (ejecución concurrente)
        f1.start();
        f2.start();

        // join() obliga al hilo principal a esperar hasta que terminen ambos hilos de lectura
        try {
            f1.join();   // Esperar a que termine f1
            f2.join();   // Esperar a que termine f2
        } catch (InterruptedException e){
            // Si el hilo principal es interrumpido, se marca el estado y se continúa
            Thread.currentThread().interrupt();
        }
    }
}
