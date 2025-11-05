

public class ua2tarea1fr1 {
    static int contador = 0;

    static class HiloContador extends Thread {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                contador++; // Acceso no sincronizado
            }
        }
    }

    public static void main(String[] args) {
        try {
            HiloContador[] hilos = new HiloContador[5];

            for (int i = 0; i < 5; i++) {
                hilos[i] = new HiloContador();
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            System.out.println("Valor final del contador (sin sincronización): " + contador);
            System.out.println("Resultado esperado: 5000");
            System.out.println("Conclusión: Al no haber sincronización" +
                    "a veces el resultado final es menor a 5000.");
        } catch (InterruptedException e) {
            System.err.println("Error: un hilo fue interrumpido inesperadamente.");
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
    }
