public class ua2tarea1fr1 {
    private static int contador = 0;
    private static final int incremento = 1000;
    private static final int numHilos = 5;

    // Hilo que incrementa el contador 1000 veces
    static class HiloSinSincronizar extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < incremento; i++) {
                contador++;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- FR1: SIN Sincronización ---");
        System.out.println("Esperado: 5000");

        // 1. Inicialización y lanzamiento de los 5 hilos
        HiloSinSincronizar h1 = new HiloSinSincronizar();
        HiloSinSincronizar h2 = new HiloSinSincronizar();
        HiloSinSincronizar h3 = new HiloSinSincronizar();
        HiloSinSincronizar h4 = new HiloSinSincronizar();
        HiloSinSincronizar h5 = new HiloSinSincronizar();

        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        // 2. Esperar (join) a que todos terminen y control de errores básico
        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar un hilo: " + e.getMessage());
        }

        System.out.println("\nResultado Final (Sin Sincronización): " + contador);

        if (contador != 5000) {
            System.out.println("Resultado incorrecto, Razon: Acceso concurrente no sincronizado.");
        }
    }
}

/*
El resultado final del contador es incorrecto (casi siempre menor que 5000).
Esto sucede porque la operación contador++ no está protegida, permitiendo que varios hilos la ejecuten simultaneamente.
Esto provoca una pérdida de datos o incrementos, ya que un hilo sobrescribe el valor leído por otro hilo antes de que este último escriba su resultado
 */