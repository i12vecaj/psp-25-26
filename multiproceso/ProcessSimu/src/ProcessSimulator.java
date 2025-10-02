import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProcessSimulator {

    // Creación de tres processos
    ProcessBuilder pb1 = new ProcessBuilder("CMD", "/c", "dir");
    ProcessBuilder pb2 = new ProcessBuilder("PING", "google.es");
    ProcessBuilder pb3 = new ProcessBuilder("PING", "google.es");


    public ProcessSimulator() throws IOException { // Creo una función donde agrupar

        Process p1 = pb1.inheritIO().start();
        Process p2 = pb2.inheritIO().start();
        Process p3 = pb3.inheritIO().start();


    }

    public void run() throws IOException, InterruptedException {
        // 1 Secuencial
        long t0 = System.currentTimeMillis(); // Empezamos a guardar el tiempo de inicio
        // Lanzamos el proceso y esperamos que termine
        pb1.start().waitFor();
        pb2.start().waitFor();
        pb3.start().waitFor();
        long tiempoSecuencial = System.currentTimeMillis() - t0; // Aqui contamos el tiempo total que ha tardado desde que inico

        // 2 Paralelo
        long p0 = System.currentTimeMillis(); // Empezamos a guardar el tiempo de inicio
        // Lanzamos todos los procesos si esperar que terminen los 3
        Process p1 = pb1.start();
        Process p2 = pb2.start();
        Process p3 = pb3.start();
        // Esperamos que temirnen
        p1.waitFor();
        p2.waitFor();
        p3.waitFor();
        long tiempoParalelo = System.currentTimeMillis() - p0; // Aqui contamos el tiempo total que ha tardado desde que inico
        /* Aqui porcedemos a escribir los log en nuestro txt
        Aqui emepzamos a abrir el proceso de escritura en el archivo resultados_multiproceso.txt
        si usamos true en modo append significa que no se sobreescribe sino que se va añadiendo al final

        */
        try (PrintWriter log = new PrintWriter(new FileWriter("logs/resultados_multiproceso.txt", true))) {
            // escribe él título para separar la ejecuciiones
            log.println("=== Sprint 1 – Ejecución ===");
            //Añade cuanto tardo la ejecucion
            log.println("Tiempo SECUENCIAL: " + tiempoSecuencial + " ms");
            log.println("Tiempo PARALELO  : " + tiempoParalelo + " ms");
            //Calcula la diferencia de cada uno
            log.println("Diferencia       : " + (tiempoSecuencial - tiempoParalelo) + " ms");
            //Inserta una linea en blanco
            log.println();
        }
        System.out.println("Log escrito en logs/resultados_multiproceso.txt");
    }
}
