/**
 * FR2: Hilos usando Runnable y método synchronized
 * Autor: Jonathan Martínez
 *
 * Resumen:
 * Este ejercicio demuestra cómo la sincronización resuelve el problema
 * de condición de carrera pero usando la interfaz Runnable. (Mucho mejor que extender Thread)
 */

public class ua2tarea1fr2runnable {
    // Variable contador
    static int contador = 0;

    // Método sincronizado
    public static synchronized void incrementarContador() {
        contador++;
    }

    // Clase Hilo con Runnable
    static class Hilo implements Runnable {
        int identificador;

        public Hilo(int numero) {
            this.identificador = numero;
        }

        @Override
        public void run() {
            try {
                System.out.println("Hilo " + identificador + " iniciado.");

                // Cada hilo incrementa el contador de forma sincronizada
                for (int i = 0; i < 1000; i++) {
                    incrementarContador(); // Método sincronizado
                }

                System.out.println("Hilo " + identificador + " finalizado.");

            } catch (Exception e) {
                System.err.println("Error en hilo " + identificador + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("=== FR2: Hilos CON sincronización (Runnable) ===");
            System.out.println("Iniciando 5 hilos.\n" +
                    "Cada uno incrementa x 1000 el contador.\n" +
                    "Valor inicial contador: " + contador);
            System.out.println("===============================================");
            System.out.println();

            Hilo tarea1 = new Hilo(1);
            Hilo tarea2 = new Hilo(2);
            Hilo tarea3 = new Hilo(3);
            Hilo tarea4 = new Hilo(4);
            Hilo tarea5 = new Hilo(5);

            // Creamos los threads para los Hilos (x implementar Runnable)
            Thread hilo1 = new Thread(tarea1);
            Thread hilo2 = new Thread(tarea2);
            Thread hilo3 = new Thread(tarea3);
            Thread hilo4 = new Thread(tarea4);
            Thread hilo5 = new Thread(tarea5);

            hilo1.start();
            hilo2.start();
            hilo3.start();
            hilo4.start();
            hilo5.start();

            // Esperar a que todos los hilos terminen (para esto se usa join)
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo5.join();

            System.out.println();
            System.out.println("=== RESULTADOS ===");
            System.out.println("Valor esperado: 5000 (5 hilos × 1000 incrementos)");
            System.out.println("Valor obtenido: " + contador);

            if (contador == 5000) {
                System.out.println("Resultado CORRECTO - La sincronización funcionó perfectamente");
                System.out.println("El método synchronized garantiza que solo un hilo modifique el contador a la vez.");
            } else {
                System.out.println("ERROR");
                System.out.println("Esto NO debería ocurrir con sincronización correcta....");
            }

        } catch (InterruptedException e) {
            System.err.println("Interrupted Exception: " + e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Restaurar estado de interrupción
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}