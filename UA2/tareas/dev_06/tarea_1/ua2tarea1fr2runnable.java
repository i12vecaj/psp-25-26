
import java.util.*;
//Nombre: Miguel Castilla Gallego
//Fecha: 05/11/2025
//Versión: 1.0


public class ua2tarea1fr2runnable {
    static int contador = 0;

    public static synchronized void incrementar() {
        contador++;
    }

    static class TareaContador implements Runnable {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                incrementar();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread[] hilos = new Thread[5];
            TareaContador tarea = new TareaContador();

            for (int i = 0; i < 5; i++) {
                hilos[i] = new Thread(tarea);
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            System.out.println("Valor final del contador (Runnable sincronizado): " + contador);
            System.out.println("Resultado esperado: 5000");
            System.out.println("Conclusión: Runnable ofrece el mismo resultado, solo cambia la forma de crear los hilos.");
        } catch (InterruptedException e) {
            System.err.println("Error: un hilo fue interrumpido inesperadamente.");
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}
