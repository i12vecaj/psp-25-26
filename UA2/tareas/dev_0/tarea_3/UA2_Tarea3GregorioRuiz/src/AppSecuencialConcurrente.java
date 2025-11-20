import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AppSecuencialConcurrente {

    /*En este programa se compara la programación secuencial y concurrente y el tiempo que
    tardan en ejecutar 3 textos largos. Para ello se ha empleado los metodos de la interfaz Runnable
    .run(secuencial) y .start y .join (concurrente) y se han comparado los tiempos.

    

    Secuencial: las tareas se ejecutan una detrás de otra en orden; el tiempo total es la suma de
    los tiempos individuales. No hay solapamiento de espera: mientras una tarea espera
    (E/S, por ejemplo), ninguna otra avanza.

    Concurrente: las tareas pueden ejecutarse solapadas en el tiempo (hilos o procesos distintos).
    El tiempo total puede ser menor porque mientras una tarea está bloqueada esperando E/S otra
    puede ejecutar trabajo; no es necesariamente la suma de los tiempos individuales.

    */
    // Runnable que se encarga de leer un fichero y escribir su contenido
    static class FilePrinter implements Runnable {
        private final String filename;

        public FilePrinter(String filename) {
            this.filename = filename;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                // Imprimir un encabezado para identificar el hilo (opcional)
                synchronized (System.out) {
                    System.out.println("---- Inicio de " + filename + " (Thread: " + Thread.currentThread().getName() + ") ----");
                }
                // Leer y mostrar línea a línea; sincronizamos cada println para evitar mezcla de líneas
                while ((line = br.readLine()) != null) {
                    synchronized (System.out) {
                        System.out.println(line);
                    }
                }
                synchronized (System.out) {
                    System.out.println("---- Fin de " + filename + " (Thread: " + Thread.currentThread().getName() + ") ----");
                }
            } catch (FileNotFoundException e) {
                System.err.println(Thread.currentThread().getName() + ": Fichero no encontrado -> " + filename);
            } catch (IOException e) {
                System.err.println(Thread.currentThread().getName() + ": Error de lectura -> " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String nombreFichero1 = "donquijote.txt";
        String nombreFichero2 = "dracula.txt";
        String nombreFichero3 = "mobydick.txt";

        // Crear hilos con la tarea Runnable
        Thread t1 = new Thread(new FilePrinter(nombreFichero1), "Lector-1");
        Thread t2 = new Thread(new FilePrinter(nombreFichero2), "Lector-2");
        Thread t3 = new Thread(new FilePrinter(nombreFichero3), "Lector-3");

        // ---- Medición secuencial ----
        System.out.println("=== Ejecución SECUENCIAL ===");
        long t_seq_start = System.currentTimeMillis();
        // Ejecutar las tareas en el hilo principal de forma secuencial
        new FilePrinter(nombreFichero1).run();
        new FilePrinter(nombreFichero2).run();
        new FilePrinter(nombreFichero3).run();
        long t_seq_end = System.currentTimeMillis();
        

        // ---- Medición concurrente ----
        System.out.println("=== Ejecución CONCURRENTE ===");
        long t_conc_start = System.currentTimeMillis();
        // Iniciar los hilos (concurrencia)
        t1.start();
        t2.start();
        t3.start();

        // Esperar a que terminen
        t1.join();
        t2.join();
        t3.join();
        long t_conc_end = System.currentTimeMillis();

        System.out.println("Tiempo total secuencial: " + (t_seq_end - t_seq_start) + " ms\n");
        System.out.println("Tiempo total concurrente: " + (t_conc_end - t_conc_start) + " ms");

        System.out.println("\nLectura concurrente finalizada.");
    }
}