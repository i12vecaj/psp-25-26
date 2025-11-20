import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        System.out.println("==============================================================\n");
        // Ejecución Secuencial
        System.out.println("== Ejecución Secuencial ==\n");
        long startTimeSecuencial = System.currentTimeMillis();
        long duracionSecuencial;

        try {
            long contador1 = 0;
            long contador2 = 0;
            long contador3 = 0;

            // Contar caracteres para el primer fichero
            try (FileReader fr1 = new FileReader("src/elquijote.txt")) {
                while (fr1.read() != -1) {
                    contador1++;
                }
            }
            System.out.println("El Quijote: " + contador1 + " caracteres");

            // Contar caracteres para el segundo fichero
            try (FileReader fr2 = new FileReader("src/lacelestina.txt")) {
                while (fr2.read() != -1) {
                    contador2++;
                }
            }
            System.out.println("La Celestina: " + contador2 + " caracteres");

            // Contar caracteres para el tercer fichero
            try (FileReader fr3 = new FileReader("src/elarboldelaciencia.txt")) {
                while (fr3.read() != -1) {
                    contador3++;
                }
            }
            System.out.println("El Árbol de la Ciencia: " + contador3 + " caracteres\n");

        } catch (FileNotFoundException e) {
            System.err.println("ERROR Fichero no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
        
        long endTimeSecuencial = System.currentTimeMillis();
        duracionSecuencial = endTimeSecuencial - startTimeSecuencial;
        System.out.println("Tiempo total E. secuencial: " + duracionSecuencial + " ms\n");

        System.out.println("==============================================================\n");

        // Ejecución Concurrente (Hilos)
        System.out.println("== Ejecución Concurrente ==\n");
        long startTimeConcurrente = System.currentTimeMillis();

        LeerFichero quijotehilo = new LeerFichero("src/elquijote.txt");
        LeerFichero celestinahilo = new LeerFichero("src/lacelestina.txt");
        LeerFichero arbolhilo = new LeerFichero("src/elarboldelaciencia.txt");

        Thread hilo1 = new Thread(quijotehilo);
        Thread hilo2 = new Thread(celestinahilo);
        Thread hilo3 = new Thread(arbolhilo);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar a los hilos: " + e.getMessage());
            e.printStackTrace();
        }

        long endTimeConcurrente = System.currentTimeMillis();

        System.out.println("El Quijote: " + quijotehilo.getCaracteres() + " caracteres");
        System.out.println("La Celestina: " + celestinahilo.getCaracteres() + " caracteres");
        System.out.println("El Árbol de la Ciencia: " + arbolhilo.getCaracteres() + " caracteres\n");
        
        long duracionConcurrente = endTimeConcurrente - startTimeConcurrente;
        System.out.println("Tiempo total E. concurrente: " + duracionConcurrente + " ms\n");

        System.out.println("==============================================================\n");

        // Comparación
        System.out.println("== Comparación ==");
        System.out.println("Tiempo Secuencial: " + duracionSecuencial + " ms");
        System.out.println("Tiempo Concurrente: " + duracionConcurrente + " ms");
    }
}
