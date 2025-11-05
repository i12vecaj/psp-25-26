/**
 * FR1: Hilos con problemas de concurrencia sin sincronización
 * Autor: Jonathan Martínez
 *
 * Resumen:
 * Este ejercicio es un claro ejemplo de la condición de carrera
 * y los problemas de sincronización
 */

public class ua2tarea1fr1 {
    // Variable compartida
    static int contador = 0;

    // Clase para los hilos
    static class Hilo extends Thread {
        int identificador;

        public Hilo(int numero) {
            this.identificador = numero;
        }

        @Override
        public void run() {
            System.out.println("Hilo " + identificador + " iniciado");

            // Cada hilo incrementa el contador 1000 veces
            for (int i = 0; i < 1000; i++) {
                contador++; // sin sincronizar
            }

            System.out.println("Hilo " + identificador + " finalizado");
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("=== FR1: Hilos sin sincronizar ===");
            System.out.println("Iniciando 5 hilos.\n" +
                    "Cada uno incrementa x 1000 el contador.\n" +
                    "Valor inicial contador: "+contador);
            System.out.println("================================");
            System.out.println();

            // Crear los 5 hilos manualmente uno a uno usando la clase HiloContador
            Hilo hilo1 = new Hilo(1);
            Hilo hilo2 = new Hilo(2);
            Hilo hilo3 = new Hilo(3);
            Hilo hilo4 = new Hilo(4);
            Hilo hilo5 = new Hilo(5);

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

            if (contador < 5000) {
                System.out.println("¡ERROR! Se han perdido " + (5000 - contador) + " incrementos");
                System.out.println("Esto es debido a la 'Condición de carrera' y el acceso no sincronizado");
                System.out.println("Cada hilo intenta acceder a la variable contador a la vez causando conflictos.");
            } else {
                System.out.println("Resultado correcto (sorprendentemente)");
            }

        } catch (InterruptedException e) {
            System.err.println("Interrupted Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}