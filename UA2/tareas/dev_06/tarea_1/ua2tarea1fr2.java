
//Nombre: Miguel Castilla Gallego
//Fecha: 05/11/2025
//Versión: 1.0

public class ua2tarea1fr2 {
    static int contador = 0;

    public static synchronized void incrementar() {
        contador++;
    }

    static class HiloContador extends Thread {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                incrementar();
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

            System.out.println("Valor final del contador (con sincronización): " + contador);
            System.out.println("Resultado esperado: 5000");
            System.out.println("Conclusión: Al sincronizar el acceso, el resultado siempre es correcto ");
        } catch (InterruptedException e) {
            System.err.println("Error: un hilo fue interrumpido inesperadamente.");
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}
